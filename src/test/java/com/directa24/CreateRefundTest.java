package com.directa24;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.directa24.base.AbstractDirecta24Test;
import com.directa24.exception.Directa24Exception;
import com.directa24.model.internal.BankAccount;
import com.directa24.model.request.CreateRefundRequest;
import com.directa24.model.response.CreateRefundResponse;

public class CreateRefundTest extends AbstractDirecta24Test {

   @Before
   public void createMocks() {

      stubFor(post(urlMatching("/v3/refunds"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("{\"refund_id\": 1000043\n}")));

   }

   @Test
   public void createRefundTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, API_KEY, SECRET_KEY);

      BankAccount bankAccount = BankAccount
            .builder()
            .bankCode("01")
            .accountNumber("3242342")
            .accountType("SAVING")
            .beneficiary("Ricardo Carlos")
            .branch("12")
            .build();

      CreateRefundRequest createRefundRequest = CreateRefundRequest
            .builder()
            .depositId(9999)
            .invoiceId("1234")
            .amount(new BigDecimal(100))
            .bankAccount(bankAccount)
            .comments("Test")
            .notificationUrl("https://yoursite.com/ipn")
            .idempotency("")
            .build();

      CreateRefundResponse createRefundResponse = directa24Test.client.createRefund(createRefundRequest);

      assertTrue(createRefundResponse != null && createRefundResponse.getRefundId() != null);

      verify(postRequestedFor(urlEqualTo("/v3/refunds")).withHeader("Content-Type", equalTo("application/json")));
   }

}
