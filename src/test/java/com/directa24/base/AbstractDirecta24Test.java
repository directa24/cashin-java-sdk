package com.directa24.base;

import org.junit.Rule;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public abstract class AbstractDirecta24Test {

   protected static final String DEPOSIT_KEY = "gFNQnUZXSE";

   protected static final String SECRET_KEY = "gUfmqFBEGvIemZhzcpkfpBMcHhyoXUxFp";

   protected static final String READ_ONLY_KEY = "EgIECaVuyW";

   @Rule
   public WireMockRule wireMockRule = new WireMockRule(8089);

}
