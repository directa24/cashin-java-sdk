package com.directa24.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.directa24.client.interceptor.DefaultHeadersInterceptor;
import com.directa24.client.util.ClientUtils;
import com.directa24.exception.Directa24Exception;
import com.directa24.model.request.BankDataRequest;
import com.directa24.model.request.CreateDepositRequest;
import com.directa24.model.request.CreateRefundRequest;
import com.directa24.model.request.DepositStatusRequest;
import com.directa24.model.request.ExchangeRateRequest;
import com.directa24.model.request.PaymentMethodRequest;
import com.directa24.model.response.BankDataResponse;
import com.directa24.model.response.CreateDepositResponse;
import com.directa24.model.response.CreateRefundResponse;
import com.directa24.model.response.DepositStatusResponse;
import com.directa24.model.response.ExchangeRateResponse;
import com.directa24.model.response.PaymentMethodResponse;
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

   private static final String REFUNDS_V3_PATH = "/v3/refunds";

   private static final String BANKS_V3_PATH = "/v3/banks";

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
         .registerModule(new JavaTimeModule())
         .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

   private static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;

   private static final int DEFAULT_READ_TIMEOUT = 30 * 1000;

   private String depositKey;

   private String apiKey;

   private String secretKey;

   private String baseUrl;

   private OkHttpClient okHttpClient;

   public Directa24Client(String depositKey, String apiKey, String secretKey, String baseUrl) {
      this(depositKey, apiKey, secretKey, baseUrl, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
   }

   public Directa24Client(String depositKey, String apiKey, String secretKey, String baseUrl, int connectTimeout, int readTimeout) {
      this.depositKey = depositKey;
      this.apiKey = apiKey;
      this.secretKey = secretKey;
      this.baseUrl = baseUrl;

      okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new DefaultHeadersInterceptor(depositKey))
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
         RequestBody body = RequestBody.create(bodyString.getBytes(StandardCharsets.UTF_8), MediaType.get("application/json"));

         String date = ClientUtils.now();
         Request.Builder requestBuilder = new Request.Builder()
               .url(baseUrl + DEPOSITS_V3_PATH)
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildDepositKeySignature(secretKey, date, depositKey, bodyString))
               .post(body);

         if (createDepositRequest.getIdempotency() != null && !createDepositRequest.getIdempotency().isEmpty()) {
            requestBuilder.header("X-Idempotency-Key", createDepositRequest.getIdempotency());
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
               .url(baseUrl + DEPOSITS_V3_PATH + "/" + depositStatusRequest.getId())
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildDepositKeySignature(secretKey, date, depositKey, null))
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
            httpBuilder.addQueryParameter("country", paymentMethodRequest.getCountry());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildApiKeySignature(apiKey))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               List<PaymentMethodResponse> paymentMethodResponse = OBJECT_MAPPER.readValue(responseBody, List.class);
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
            httpBuilder.addQueryParameter("country", exchangeRateRequest.getCountry());
         }
         if (exchangeRateRequest.getAmount() != null) {
            httpBuilder.addQueryParameter("amount", exchangeRateRequest.getAmount().toString());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildApiKeySignature(apiKey))
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
    * @return List<BankDataResponse> Banks information
    * @throws Directa24Exception if underlying service fails
    */
   public List<BankDataResponse> getBanks(BankDataRequest bankDataRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();

         HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl + BANKS_V3_PATH).newBuilder();
         if (bankDataRequest.getCountry() != null) {
            httpBuilder.addQueryParameter("country", bankDataRequest.getCountry());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildApiKeySignature(apiKey))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               List<BankDataResponse> bankDataResponses = OBJECT_MAPPER.readValue(responseBody, List.class);
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
         RequestBody body = RequestBody.create(bodyString.getBytes(StandardCharsets.UTF_8), MediaType.get("application/json"));

         String date = ClientUtils.now();
         Request.Builder requestBuilder = new Request.Builder()
               .url(baseUrl + REFUNDS_V3_PATH)
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildDepositKeySignature(secretKey, date, depositKey, bodyString))
               .post(body);

         if (createRefundRequest.getIdempotency() != null && !createRefundRequest.getIdempotency().isEmpty()) {
            requestBuilder.header("X-Idempotency-Key", createRefundRequest.getIdempotency());
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

}
