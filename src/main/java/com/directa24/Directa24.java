package com.directa24;

import com.directa24.client.Directa24Client;

public class Directa24 {

   public Directa24Client client;

   public Directa24(String login, String secretKey, String baseUrl, String webUrl) {
      client = new Directa24Client(login, secretKey, baseUrl, webUrl);
   }

   public static class Sandbox extends Directa24 {
      public Sandbox(String login, String secretKey) {
         super(login, secretKey, "http://127.0.0.1:8080", "https://www.sandbox.directa24.com");
      }
   }

   public static class Production extends Directa24 {
      public Production(String login, String secretKey) {
         super(login, secretKey, "https://api.directa24.com", "https://www.directa24.com");
      }
   }

   public Directa24 connectTimeout(int connectTimeout) {
      client.setConnectTimeout(connectTimeout);
      return this;
   }

   public Directa24 readTimeout(int readTimeout) {
      client.setReadTimeout(readTimeout);
      return this;
   }

}
