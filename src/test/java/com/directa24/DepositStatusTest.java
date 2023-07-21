package com.directa24;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.directa24.base.AbstractDirecta24Test;
import com.directa24.exception.Directa24Exception;
import com.directa24.model.request.DepositStatusRequest;
import com.directa24.model.response.DepositStatusResponse;

public class DepositStatusTest extends AbstractDirecta24Test {

   @Before
   public void createMocks() {

      stubFor(get(urlMatching("/v3/deposits/123456"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBodyFile("deposit_status_response.json")));

      stubFor(get(urlMatching("/v3/deposits/999999"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse().withStatus(404).withHeader("Content-Type", "application/json")));
   }

   @Test
   public void depositStatusTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, SECRET_KEY);

      DepositStatusRequest depositStatusRequest = DepositStatusRequest.builder() //
                                                                      .id(123456) //
                                                                      .build();

      DepositStatusResponse depositStatusResponse = directa24Test.client.depositStatus(depositStatusRequest);

      assertTrue(depositStatusResponse != null);
      assertEquals(depositStatusResponse.getStatus(), "PENDING");

      verify(getRequestedFor(urlEqualTo("/v3/deposits/" + 123456)).withHeader("Content-Type", equalTo("application/json")));
   }

   @Test
   public void depositNotFoundTest() {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, SECRET_KEY);

      DepositStatusRequest depositStatusRequest = DepositStatusRequest.builder() //
                                                                      .id(999999) //
                                                                      .build();

      DepositStatusResponse depositStatusResponse = null;
      try {
         depositStatusResponse = directa24Test.client.depositStatus(depositStatusRequest);
         fail("Deposit doesn't exists");
      } catch (Directa24Exception e) {
      }

      assertTrue(depositStatusResponse == null);
   }

}
