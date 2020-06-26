package com.directa24.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.directa24.client.interceptor.DefaultHeadersInterceptor;
import com.directa24.client.util.ClientUtils;
import com.directa24.exception.Directa24Exception;
import com.directa24.model.request.CreateDepositRequest;
import com.directa24.model.request.CurrencyExchangeRequest;
import com.directa24.model.request.DepositStatusRequest;
import com.directa24.model.request.PaymentMethodRequest;
import com.directa24.model.response.CreateDepositResponse;
import com.directa24.model.response.CurrencyExchangeResponse;
import com.directa24.model.response.DepositStatusResponse;
import com.directa24.model.response.PaymentMethodResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Directa24Client {

   private static final String DEPOSIT_V3_PATH = "/v3/deposit/";

   private static final String PAYMENT_METHODS_V3_PATH = "/v3/payment_methods";

   private static final String CURRENCY_EXCHANGE_V3_PATH = "/v3/currency_exchange";

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

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
    * Create a deposit.
    *
    * @param createDepositRequest
    * @return CreateDepositResponse
    * @throws Directa24Exception
    */
   public CreateDepositResponse createDeposit(CreateDepositRequest createDepositRequest) throws Directa24Exception {
      try {
         String bodyString = OBJECT_MAPPER.writeValueAsString(createDepositRequest);
         RequestBody body = RequestBody.create(bodyString.getBytes(StandardCharsets.UTF_8), MediaType.get("application/json"));

         String date = ClientUtils.now();
         Request.Builder requestBuilder = new Request.Builder()
               .url(baseUrl + DEPOSIT_V3_PATH)
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
    * Return a deposit status.
    *
    * @param depositStatusRequest
    * @return DepositStatusResponse
    * @throws Directa24Exception
    */
   public DepositStatusResponse depositStatus(DepositStatusRequest depositStatusRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         Request request = new Request.Builder()
               .url(baseUrl + DEPOSIT_V3_PATH + depositStatusRequest.getId())
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
    * Return payment methods.
    *
    * @param paymentMethodRequest
    * @return List<PaymentMethodResponse>
    * @throws Directa24Exception
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

   public CurrencyExchangeResponse currencyExchange(CurrencyExchangeRequest currencyExchangeRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         HttpUrl.Builder httpBuilder = HttpUrl.parse(baseUrl + CURRENCY_EXCHANGE_V3_PATH).newBuilder();
         if (currencyExchangeRequest.getCountry() != null) {
            httpBuilder.addQueryParameter("country", currencyExchangeRequest.getCountry());
         }
         if (currencyExchangeRequest.getAmount() != null) {
            httpBuilder.addQueryParameter("amount", currencyExchangeRequest.getAmount().toString());
         }
         Request request = new Request.Builder()
               .url(httpBuilder.build())
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildApiKeySignature(apiKey))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.isSuccessful()) {
               CurrencyExchangeResponse currencyExchangeResponse = OBJECT_MAPPER.readValue(responseBody, CurrencyExchangeResponse.class);
               return currencyExchangeResponse;
            } else {
               throw new Directa24Exception(responseBody);
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

}
