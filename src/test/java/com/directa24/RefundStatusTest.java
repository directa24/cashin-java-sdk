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
import com.directa24.model.request.RefundStatusRequest;
import com.directa24.model.response.RefundStatusResponse;

public class RefundStatusTest extends AbstractDirecta24Test {

   @Before
   public void createMocks() {

      stubFor(get(urlMatching("/v3/refunds/123456"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody(
                        "{\"deposit_id\": 300537729,\"merchant_invoice_id\": \"postmanTest971574817\",\"status\": \"PENDING\",\"amount\": 1000.00}")));

      stubFor(get(urlMatching("/v3/refunds/999999"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse().withStatus(404).withHeader("Content-Type", "application/json")));
   }

   @Test
   public void refundStatusTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, API_KEY, SECRET_KEY);

      RefundStatusRequest refundStatusRequest = RefundStatusRequest.builder() //
                                                                   .id(123456) //
                                                                   .build();

      RefundStatusResponse refundStatusResponse = directa24Test.client.refundStatus(refundStatusRequest);

      assertTrue(refundStatusResponse != null);
      assertEquals(refundStatusResponse.getStatus(), "PENDING");

      verify(getRequestedFor(urlEqualTo("/v3/refunds/" + 123456)).withHeader("Content-Type", equalTo("application/json")));
   }

   @Test
   public void refundNotFoundTest() {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, API_KEY, SECRET_KEY);

      RefundStatusRequest refundStatusRequest = RefundStatusRequest.builder() //
                                                                   .id(999999) //
                                                                   .build();

      RefundStatusResponse refundStatusResponse = null;
      try {
         refundStatusResponse = directa24Test.client.refundStatus(refundStatusRequest);
         fail("Refund doesn't exists");
      } catch (Directa24Exception e) {
      }

      assertTrue(refundStatusResponse == null);
   }

}
