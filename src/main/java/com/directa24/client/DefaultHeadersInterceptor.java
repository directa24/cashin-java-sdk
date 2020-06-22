package com.directa24.client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultHeadersInterceptor implements Interceptor {

   private String login;

   public DefaultHeadersInterceptor(String login) {
      this.login = login;
   }

   public Response intercept(Interceptor.Chain chain) throws IOException {

      Request originalRequest = chain.request();
      Request requestWithHeaders = originalRequest
            .newBuilder()
            .header("Content-Type", "application/json")
            .header("X-Date", LocalDateTime.now(ZoneOffset.UTC).toString())
            .header("X-Login", login)
            .build();

      return chain.proceed(requestWithHeaders);
   }

}
