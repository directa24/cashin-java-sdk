package com.directa24.base;

import org.junit.Rule;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public abstract class AbstractDirecta24Test {

   protected static final String DEPOSIT_KEY = "1955f2d";

   protected static final String API_KEY = "eJ3Ldt6Xma";

   protected static final String SECRET_KEY = "4lph0ns3";

   @Rule
   public WireMockRule wireMockRule = new WireMockRule(8089);

}
