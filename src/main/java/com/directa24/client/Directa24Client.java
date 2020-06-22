package com.directa24.client;

import java.io.IOException;
import java.util.Map;

import com.directa24.exception.Directa24Exception;
import com.directa24.model.deposit.create.CreateDepositRequest;
import com.directa24.model.deposit.create.CreateDepositResponse;
import com.directa24.model.deposit.status.DepositStatusRequest;
import com.directa24.model.deposit.status.DepositStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Data
public class Directa24Client {

   private static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
   private static final int DEFAULT_READ_TIMEOUT = 30 * 1000;

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
   private int readTimeout = DEFAULT_READ_TIMEOUT;

   private String login;
   private String secretKey;

   private String baseUrl;

   private String webUrl;

   private OkHttpClient okHttpClient;

   public Directa24Client(String login, String secretKey, String baseUrl, String webUrl) {
      this.login = login;
      this.secretKey = secretKey;
      this.baseUrl = baseUrl;
      this.webUrl = webUrl;

      okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new DefaultHeadersInterceptor(login))
            .build();
   }

   public CreateDepositResponse createDeposit(CreateDepositRequest createDepositRequest) throws Directa24Exception {
      return null;
   }

   public DepositStatusResponse depositStatus(DepositStatusRequest depositStatusRequest) throws Directa24Exception {
      try {
         Request request = new Request.Builder()
                                      .url(baseUrl + "/v3/deposit/" + depositStatusRequest.getId())
                                      .header("Authorization", SignatureUtil.hmacSha256(secretKey, depositStatusRequest))
                                      .build();

         try (Response response = okHttpClient.newCall(request).execute()) {
            if (response != null) {
               String responseBody = response.body().string();
               Map<String, String> responseBodyMap = OBJECT_MAPPER.readValue(responseBody, Map.class);

               if (responseBodyMap.get("code") != null) {
                  throw new Directa24Exception(responseBody);
               } else {
                  DepositStatusResponse depositStatusResponse = OBJECT_MAPPER.readValue(responseBody, DepositStatusResponse.class);
                  return depositStatusResponse;
               }
            } else {
               throw new Directa24Exception("Empty response");
            }
         }
      } catch (IOException e) {
         throw new Directa24Exception(e);
      }
   }

}
