package com.directa24;

import com.directa24.client.Directa24Client;

public class Directa24 {

   public Directa24Client client;

   public Directa24(String login, String secretKey, String baseUrl) {
      client = new Directa24Client(login, secretKey, baseUrl);
   }

   public Directa24(String login, String secretKey, String baseUrl, int connectTimeout, int readTimeout) {
      client = new Directa24Client(login, secretKey, baseUrl, connectTimeout, readTimeout);
   }

   public static class Sandbox extends Directa24 {

      /**
       * Create a client for Sandbox environment
       */
      public Sandbox(String login, String secretKey) {
         super(login, secretKey, "http://127.0.0.1:8080");
      }

      /**
       * Create a client for Sandbox environment with custom connect timeout and read tiemout
       */
      public Sandbox(String login, String secretKey, int connectTimeout, int readTimeout) {
         super(login, secretKey, "http://127.0.0.1:8080", connectTimeout, readTimeout);
      }
   }

   public static class Production extends Directa24 {

      /**
       * Create a client for Production environment
       */
      public Production(String login, String secretKey) {
         super(login, secretKey, "https://api.directa24.com");
      }

      /**
       * Create a client for Production environment with custom connect timeout and read tiemout
       */
      public Production(String login, String secretKey, int connectTimeout, int readTimeout) {
         super(login, secretKey, "https://api.directa24.com", connectTimeout, readTimeout);
      }
   }

}
