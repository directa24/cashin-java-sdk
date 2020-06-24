package com.directa24.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.directa24.client.interceptor.DefaultHeadersInterceptor;
import com.directa24.client.util.ClientUtils;
import com.directa24.exception.Directa24Exception;
import com.directa24.model.request.CreateDepositRequest;
import com.directa24.model.request.DepositStatusRequest;
import com.directa24.model.response.CreateDepositResponse;
import com.directa24.model.response.DepositStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Directa24Client {

   private static final String DEPOSIT_V3_PATH = "/v3/deposit/";

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

   private static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;

   private static final int DEFAULT_READ_TIMEOUT = 30 * 1000;

   private String login;

   private String secretKey;

   private String baseUrl;

   private OkHttpClient okHttpClient;

   public Directa24Client(String login, String secretKey, String baseUrl) {
      new Directa24Client(login, secretKey, baseUrl, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
   }

   public Directa24Client(String login, String secretKey, String baseUrl, int connectTimeout, int readTimeout) {
      this.login = login;
      this.secretKey = secretKey;
      this.baseUrl = baseUrl;

      okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new DefaultHeadersInterceptor(login))
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build();
   }

   /**
    * Create a deposit
    */
   public CreateDepositResponse createDeposit(CreateDepositRequest createDepositRequest) throws Directa24Exception {
      try {
         String bodyString = OBJECT_MAPPER.writeValueAsString(createDepositRequest);
         RequestBody body = RequestBody.create(bodyString.getBytes(StandardCharsets.UTF_8), MediaType.get("application/json"));

         String date = ClientUtils.now();
         Request.Builder requestBuilder = new Request.Builder()
               .url(baseUrl + DEPOSIT_V3_PATH)
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildSignature(secretKey, date, login, bodyString))
               .post(body);

         if (createDepositRequest.getIdempotency() != null && !createDepositRequest.getIdempotency().isEmpty()) {
            requestBuilder.header("X-Idempotency-Key", createDepositRequest.getIdempotency());
         }

         Request request = requestBuilder.build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            Map<String, String> responseBodyMap = OBJECT_MAPPER.readValue(responseBody, Map.class);

            if (responseBodyMap.get("code") != null) {
               throw new Directa24Exception(responseBody);
            } else {
               CreateDepositResponse createDepositResponse = OBJECT_MAPPER.readValue(responseBody, CreateDepositResponse.class);
               return createDepositResponse;
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

   /**
    * Return a deposit status
    */
   public DepositStatusResponse depositStatus(DepositStatusRequest depositStatusRequest) throws Directa24Exception {
      try {
         String date = ClientUtils.now();
         Request request = new Request.Builder()
               .url(baseUrl + DEPOSIT_V3_PATH + depositStatusRequest.getId())
               .header("X-Date", date)
               .header("Authorization", ClientUtils.buildSignature(secretKey, date, login, null))
               .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            Map<String, String> responseBodyMap = OBJECT_MAPPER.readValue(responseBody, Map.class);

            if (responseBodyMap.get("code") != null) {
               throw new Directa24Exception(responseBody);
            } else {
               DepositStatusResponse depositStatusResponse = OBJECT_MAPPER.readValue(responseBody, DepositStatusResponse.class);
               return depositStatusResponse;
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

}
