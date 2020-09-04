package com.directa24.client.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ReadOnlyHeadersInterceptor implements Interceptor {

   public ReadOnlyHeadersInterceptor() {

   }

   public Response intercept(Chain chain) throws IOException {

      Request originalRequest = chain.request();
      Request requestWithHeaders = originalRequest.newBuilder() //
                                                  .header("Content-Type", "application/json") //
                                                  .build();

      return chain.proceed(requestWithHeaders);
   }

}
