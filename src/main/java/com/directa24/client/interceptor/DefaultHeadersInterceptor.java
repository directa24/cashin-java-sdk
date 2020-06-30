package com.directa24.client.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultHeadersInterceptor implements Interceptor {

   private String depositKey;

   public DefaultHeadersInterceptor(String depositKey) {
      this.depositKey = depositKey;
   }

   public Response intercept(Interceptor.Chain chain) throws IOException {

      Request originalRequest = chain.request();
      Request requestWithHeaders = originalRequest.newBuilder() //
                                                  .header("Content-Type", "application/json") //
                                                  .header("X-Login", depositKey) //
                                                  .build();

      return chain.proceed(requestWithHeaders);
   }

}
