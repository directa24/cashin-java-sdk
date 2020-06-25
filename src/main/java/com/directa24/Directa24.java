package com.directa24;

import com.directa24.client.Directa24Client;

public class Directa24 {

   public Directa24Client client;

   public Directa24(String depositKey, String apiKey, String secretKey, String baseUrl) {
      client = new Directa24Client(depositKey, apiKey, secretKey, baseUrl);
   }

   public Directa24(String depositKey, String apiKey, String secretKey, String baseUrl, int connectTimeout, int readTimeout) {
      client = new Directa24Client(depositKey, apiKey, secretKey, baseUrl, connectTimeout, readTimeout);
   }

   public static class Sandbox extends Directa24 {

      /**
       * Create a client for Sandbox environment.
       *
       * @param depositKey
       * @param apiKey
       * @param secretKey
       */
      public Sandbox(String depositKey, String apiKey, String secretKey) {
         super(depositKey, apiKey, secretKey, "http://127.0.0.1:8080");
      }

      /**
       * Create a client for Sandbox environment with custom connect timeout and read timeout.
       *
       * @param depositKey
       * @param apiKey
       * @param secretKey
       * @param connectTimeout
       * @param readTimeout
       */
      public Sandbox(String depositKey, String apiKey, String secretKey, int connectTimeout, int readTimeout) {
         super(depositKey, apiKey, secretKey, "http://127.0.0.1:8080", connectTimeout, readTimeout);
      }
   }

   public static class Production extends Directa24 {

      /**
       * Create a client for Production environment.
       *
       * @param depositKey
       * @param apiKey
       * @param secretKey
       */
      public Production(String depositKey, String apiKey, String secretKey) {
         super(depositKey, apiKey, secretKey, "https://api.directa24.com");
      }

      /**
       * Create a client for Production environment with custom connect timeout and read timeout.
       *
       * @param depositKey
       * @param apiKey
       * @param secretKey
       * @param connectTimeout
       * @param readTimeout
       */
      public Production(String depositKey, String apiKey, String secretKey, int connectTimeout, int readTimeout) {
         super(depositKey, apiKey, secretKey, "https://api.directa24.com", connectTimeout, readTimeout);
      }
   }

   public static class Test extends Directa24 {

      /**
       * Create a client for Test environment.
       *
       * @param depositKey
       * @param apiKey
       * @param secretKey
       */
      public Test(String depositKey, String apiKey, String secretKey) {
         super(depositKey, apiKey, secretKey, "http://localhost:8089");
      }

   }

}
