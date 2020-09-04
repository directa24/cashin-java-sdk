package com.directa24;

import com.directa24.client.Directa24Client;

public class Directa24 {

   private static final String SANDBOX_BASE_URL = "https://api-stg.directa24.com";

   private static final String PRODUCTION_BASE_URL = "https://api.directa24.com";

   private static final String TEST_BASE_URL = "http://localhost:8089";

   public Directa24Client client;

   public Directa24(String depositKey, String secretKey, String baseUrl) {
      client = new Directa24Client(depositKey, secretKey, baseUrl);
   }

   public Directa24(String depositKey, String secretKey, String baseUrl, int connectTimeout, int readTimeout) {
      client = new Directa24Client(depositKey, secretKey, baseUrl, connectTimeout, readTimeout);
   }

   public Directa24(String readOnlyKey, String baseUrl) {
      client = new Directa24Client(readOnlyKey, baseUrl);
   }

   public Directa24(String readOnlyKey, String baseUrl, int connectTimeout, int readTimeout) {
      client = new Directa24Client(readOnlyKey, baseUrl, connectTimeout, readTimeout);
   }

   public static class Sandbox extends Directa24 {

      /**
       * Create a client for Sandbox environment.
       *
       * @param depositKey Directa 24's deposit key
       * @param secretKey  Directa 24's secret key
       */
      public Sandbox(String depositKey, String secretKey) {
         super(depositKey, secretKey, SANDBOX_BASE_URL);
      }

      /**
       * Create a client for Sandbox environment with custom connect timeout and read timeout.
       *
       * @param depositKey     Directa 24's deposit key
       * @param secretKey      Directa 24's secret key
       * @param connectTimeout Connect timeout in milliseconds
       * @param readTimeout    Read timeout in milliseconds
       */
      public Sandbox(String depositKey, String secretKey, int connectTimeout, int readTimeout) {
         super(depositKey, secretKey, SANDBOX_BASE_URL, connectTimeout, readTimeout);
      }

      /**
       * Create a read only client for Sandbox environment.
       *
       * @param readOnlyKey Directa 24's read only key
       */
      public Sandbox(String readOnlyKey) {
         super(readOnlyKey, SANDBOX_BASE_URL);
      }

      /**
       * Create a read only client for Sandbox environment with custom connect timeout and read timeout.
       *
       * @param readOnlyKey    Directa 24's read only key
       * @param connectTimeout Connect timeout in milliseconds
       * @param readTimeout    Read timeout in milliseconds
       */
      public Sandbox(String readOnlyKey, int connectTimeout, int readTimeout) {
         super(readOnlyKey, SANDBOX_BASE_URL, connectTimeout, readTimeout);
      }
   }

   public static class Production extends Directa24 {

      /**
       * Create a client for Production environment.
       *
       * @param depositKey Directa 24's deposit key
       * @param secretKey  Directa 24's secret key
       */
      public Production(String depositKey, String secretKey) {
         super(depositKey, secretKey, PRODUCTION_BASE_URL);
      }

      /**
       * Create a client for Production environment with custom connect timeout and read timeout.
       *
       * @param depositKey     Directa 24's deposit key
       * @param secretKey      Directa 24's secret key
       * @param connectTimeout Connect timeout in milliseconds
       * @param readTimeout    Read timeout in milliseconds
       */
      public Production(String depositKey, String secretKey, int connectTimeout, int readTimeout) {
         super(depositKey, secretKey, PRODUCTION_BASE_URL, connectTimeout, readTimeout);
      }

      /**
       * Create a read only client for Production environment.
       *
       * @param readOnlyKey Directa 24's read only key
       */
      public Production(String readOnlyKey) {
         super(readOnlyKey, PRODUCTION_BASE_URL);
      }

      /**
       * Create a read only client for Production environment with custom connect timeout and read timeout.
       *
       * @param readOnlyKey    Directa 24's read only key
       * @param connectTimeout Connect timeout in milliseconds
       * @param readTimeout    Read timeout in milliseconds
       */
      public Production(String readOnlyKey, int connectTimeout, int readTimeout) {
         super(readOnlyKey, PRODUCTION_BASE_URL, connectTimeout, readTimeout);
      }
   }

   public static class Test extends Directa24 {

      /**
       * Create a client for Test environment.
       *
       * @param depositKey Directa 24's deposit key
       * @param secretKey  Directa 24's secret key
       */
      public Test(String depositKey, String secretKey) {
         super(depositKey, secretKey, TEST_BASE_URL);
      }

      /**
       * Create a read only client for Test environment.
       *
       * @param readOnlyKey Directa 24's read only key
       */
      public Test(String readOnlyKey) {
         super(readOnlyKey, TEST_BASE_URL);
      }

   }

}
