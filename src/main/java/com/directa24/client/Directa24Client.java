package com.directa24.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.directa24.client.interceptor.DepositHeadersInterceptor;
import com.directa24.client.interceptor.ReadOnlyHeadersInterceptor;
import com.directa24.client.util.ClientUtils;
import com.directa24.exception.Directa24Exception;
import com.directa24.model.request.BankDataRequest;
import com.directa24.model.request.CreateDepositRequest;
import com.directa24.model.request.CreateRefundRequest;
import com.directa24.model.request.DepositStatusRequest;
import com.directa24.model.request.ExchangeRateRequest;
import com.directa24.model.request.PaymentMethodRequest;
import com.directa24.model.request.RefundStatusRequest;
import com.directa24.model.request.StateCountryRequest;
import com.directa24.model.response.BankDataResponse;
import com.directa24.model.response.CreateDepositResponse;
import com.directa24.model.response.CreateRefundResponse;
import com.directa24.model.response.DepositStatusResponse;
import com.directa24.model.response.ExchangeRateResponse;
import com.directa24.model.response.PaymentMethodResponse;
import com.directa24.model.response.RefundStatusResponse;
import com.directa24.model.response.StateCountryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Directa24Client {

   private static final String DEPOSITS_V3_PATH = "/v3/deposits";

   private static final String PAYMENT_METHODS_V3_PATH = "/v3/payment_methods";

   private static final String EXCHANGE_RATE_V3_PATH = "/v3/exchange_rates";

   private static final String EXCHANGE_RATE_CRYPTO_PATH = "/crypto";

   private static final String REFUNDS_V3_PATH = "/v3/refunds";

   private static final String BANKS_V3_PATH = "/v3/banks";

   private static final String STATE_COUNTRY_PATH = "/v3/states";

   private static final String FORWARD_SLASH = "/";

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
         .registerModule(new JavaTimeModule())
         .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

   private static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;

   private static final int DEFAULT_READ_TIMEOUT = 30 * 1000;

   private static final String HEADER_DATE = "X-Date";

   private static final String HEADER_AUTHORIZATION = "Authorization";

   private static final String HEADER_IDEMPOTENCY_KEY = "X-Idempotency-Key";

   private static final String MEDIA_TYPE = "application/json";

   private static final String QUERY_PARAM_COUNTRY = "country";

   private static final String QUERY_PARAM_AMOUNT = "amount";

   private static final String QUERY_PARAM_CURRENCY = "currency";

   private static final String QUERY_PARAM_CRYPTO = "crypto";

   private String depositKey;

   private String secretKey;

   private String readOnlyKey;

   private String baseUrl;

   private OkHttpClient okHttpClient;

   public Directa24Client(String readOnlyKey, String baseUrl) {
      this(readOnlyKey, baseUrl, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
   }

   public Directa24Client(String readOnlyKey, String baseUrl, int connectTimeout, int readTimeout) {
      this.readOnlyKey = readOnlyKey;
      this.baseUrl = baseUrl;

      okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new ReadOnlyHeadersInterceptor())
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build();
   }

   public Directa24Client(String depositKey, String secretKey, String baseUrl) {
      this(depositKey, secretKey, baseUrl, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
   }

   public Directa24Client(String depositKey, String secretKey, String baseUrl, int connectTimeout, int readTimeout) {
      this.depositKey = depositKey;
      this.secretKey = secretKey;
      this.baseUrl = baseUrl;

      okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new DepositHeadersInterceptor(depositKey))
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build();
   }

   /**
    * Creates a deposit.
    *
    * @param createDepositRequest Request object
    * @return CreateDepositResponse object
    * @throws Directa24Exception if underlying service fails
    */
   public CreateDepositResponse createDeposit(CreateDepositRequest createDepositRequest) throws Directa24Exception {
      try {
         String bodyString = OBJECT_MAPPER.writeValueAsString(createDepositRequest);
         RequestBody body = RequestBody.create(bodyString.getBytes(StandardCharsets.UTF_8), MediaType.get(MEDIA_TYPE));

         String date = ClientUtils.now();
         Request.Builder requestBuilder = new Request.Builder()
               .url(baseUrl + DEPOSITS_V3_PATH)
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildDepositSignature(secretKey, date, depositKey, bodyString))
               .post(body);

         if (createDepositRequest.getIdempotency() != null && !createDepositRequest.getIdempotency().isEmpty()) {
            requestBuilder.header(HEADER_IDEMPOTENCY_KEY, createDepositRequest.getIdempotency());
         }

         Request request = requestBuilder.build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               CreateDepositResponse createDepositResponse = OBJECT_MAPPER.readValue(responseBody, CreateDepositResponse.class);
               return createDepositResponse;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Returns a deposit status.
    *
    * @param depositStatusRequest Request object
    * @return DepositStatusResponse object
    * @throws Directa24Exception if underlying service fails
    */
   public DepositStatusResponse depositStatus(DepositStatusRequest depositStatusRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         Request request = new Request.Builder()
               .url(baseUrl + DEPOSITS_V3_PATH + FORWARD_SLASH + depositStatusRequest.getId())
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildDepositSignature(secretKey, date, depositKey, null))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               DepositStatusResponse depositStatusResponse = OBJECT_MAPPER.readValue(responseBody, DepositStatusResponse.class);
               return depositStatusResponse;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Returns payment methods.
    *
    * @param paymentMethodRequest Request object
    * @return Payment methods list
    * @throws Directa24Exception if underlying service fails
    */
   public List<PaymentMethodResponse> paymentMethods(PaymentMethodRequest paymentMethodRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl + PAYMENT_METHODS_V3_PATH).newBuilder();
         if (paymentMethodRequest.getCountry() != null) {
            httpBuilder.addQueryParameter(QUERY_PARAM_COUNTRY, paymentMethodRequest.getCountry());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildReadOnlySignature(readOnlyKey))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               List<PaymentMethodResponse> paymentMethodResponse = OBJECT_MAPPER.readValue(responseBody,
                     new TypeReference<List<PaymentMethodResponse>>() {

                     });
               return paymentMethodResponse;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Exchange rates.
    *
    * @param exchangeRateRequest Request object
    * @return ExchangeRateResponse object
    * @throws Directa24Exception if underlying service fails
    */
   public ExchangeRateResponse exchangeRates(ExchangeRateRequest exchangeRateRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl + EXCHANGE_RATE_V3_PATH).newBuilder();
         if (exchangeRateRequest.getCountry() != null) {
            httpBuilder.addQueryParameter(QUERY_PARAM_COUNTRY, exchangeRateRequest.getCountry());
         }
         if (exchangeRateRequest.getAmount() != null) {
            httpBuilder.addQueryParameter(QUERY_PARAM_AMOUNT, exchangeRateRequest.getAmount().toString());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildReadOnlySignature(readOnlyKey))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               ExchangeRateResponse exchangeRateResponse = OBJECT_MAPPER.readValue(responseBody, ExchangeRateResponse.class);
               return exchangeRateResponse;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Exchange crypto rates.
    *
    * @param exchangeRateRequest Request object
    * @return ExchangeRateResponse object
    * @throws Directa24Exception if underlying service fails
    */
   public ExchangeRateResponse exchangeRatesCrypto(ExchangeRateRequest exchangeRateRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl + EXCHANGE_RATE_V3_PATH + EXCHANGE_RATE_CRYPTO_PATH).newBuilder();
         if (exchangeRateRequest.getCurrency() != null) {
            httpBuilder.addQueryParameter(QUERY_PARAM_CURRENCY, exchangeRateRequest.getCurrency());
         }
         if (exchangeRateRequest.getAmount() != null) {
            httpBuilder.addQueryParameter(QUERY_PARAM_AMOUNT, exchangeRateRequest.getAmount().toString());
         }
         if (exchangeRateRequest.getCryptoCurrency() != null) {
            httpBuilder.addQueryParameter(QUERY_PARAM_CRYPTO, exchangeRateRequest.getCryptoCurrency());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildReadOnlySignature(readOnlyKey))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               ExchangeRateResponse exchangeRateResponse = OBJECT_MAPPER.readValue(responseBody, ExchangeRateResponse.class);
               return exchangeRateResponse;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Returns a list of available banks.
    *
    * @param bankDataRequest Request object
    * @return Banks information list
    * @throws Directa24Exception if underlying service fails
    */
   public List<BankDataResponse> getBanks(BankDataRequest bankDataRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();

         HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl + BANKS_V3_PATH).newBuilder();
         if (bankDataRequest.getCountry() != null) {
            httpBuilder.addQueryParameter(QUERY_PARAM_COUNTRY, bankDataRequest.getCountry());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildReadOnlySignature(readOnlyKey))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               List<BankDataResponse> bankDataResponses = OBJECT_MAPPER.readValue(responseBody, new TypeReference<List<BankDataResponse>>() {

               });
               return bankDataResponses;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Creates a refund.
    *
    * @param createRefundRequest Request object
    * @return CreateRefundResponse object
    * @throws Directa24Exception if underlying service fails
    */
   public CreateRefundResponse createRefund(CreateRefundRequest createRefundRequest) throws Directa24Exception {
      try {
         String bodyString = OBJECT_MAPPER.writeValueAsString(createRefundRequest);
         RequestBody body = RequestBody.create(bodyString.getBytes(StandardCharsets.UTF_8), MediaType.get(MEDIA_TYPE));

         String date = ClientUtils.now();
         Request.Builder requestBuilder = new Request.Builder()
               .url(baseUrl + REFUNDS_V3_PATH)
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildDepositSignature(secretKey, date, depositKey, bodyString))
               .post(body);

         if (createRefundRequest.getIdempotency() != null && !createRefundRequest.getIdempotency().isEmpty()) {
            requestBuilder.header(HEADER_IDEMPOTENCY_KEY, createRefundRequest.getIdempotency());
         }

         Request request = requestBuilder.build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               CreateRefundResponse createRefundResponse = OBJECT_MAPPER.readValue(responseBody, CreateRefundResponse.class);
               return createRefundResponse;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Returns a refund status.
    *
    * @param refundStatusRequest Request object
    * @return RefundStatusResponse object
    * @throws Directa24Exception if underlying service fails
    */
   public RefundStatusResponse refundStatus(RefundStatusRequest refundStatusRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         Request request = new Request.Builder()
               .url(baseUrl + REFUNDS_V3_PATH + FORWARD_SLASH + refundStatusRequest.getId())
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildDepositSignature(secretKey, date, depositKey, null))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               RefundStatusResponse refundStatusResponse = OBJECT_MAPPER.readValue(responseBody, RefundStatusResponse.class);
               return refundStatusResponse;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Returns states.
    *
    * @param stateCountryRequest Request object
    * @return State countries list
    * @throws Directa24Exception if underlying service fails
    */
   public List<StateCountryResponse> stateCountryResponses(StateCountryRequest stateCountryRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl + STATE_COUNTRY_PATH).newBuilder();
         if (stateCountryRequest.getCountry() != null) {
            httpBuilder.addQueryParameter(QUERY_PARAM_COUNTRY, stateCountryRequest.getCountry());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header(HEADER_DATE, date)
               .header(HEADER_AUTHORIZATION, ClientUtils.buildReadOnlySignature(readOnlyKey))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               List<StateCountryResponse> stateCountryResponses = OBJECT_MAPPER.readValue(responseBody,
                     new TypeReference<List<StateCountryResponse>>() {

                     });
               return stateCountryResponses;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

}
