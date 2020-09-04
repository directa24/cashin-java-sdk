package com.directa24;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.directa24.base.AbstractDirecta24Test;
import com.directa24.exception.Directa24Exception;
import com.directa24.model.request.PaymentMethodRequest;
import com.directa24.model.response.PaymentMethodResponse;

public class PaymentMethodsTest extends AbstractDirecta24Test {

   @Before
   public void createMocks() {
      stubFor(get(urlEqualTo("/v3/payment_methods"))
            .withHeader("Authorization", equalTo("Bearer " + READ_ONLY_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody("[{\"country\":\"BR\",\"code\":\"BB\","
                        + "\"name\":\"Banco Brasil\",\"type\":\"BANK_TRANSFER\",\"status\":\"OK\",\"logo\":\"https://resources.directa24"
                        + ".com/cashin/payment_method/square/BB.svg\"},"
                        + "{\"country\":\"MX\",\"code\":\"VI\",\"name\":\"Visa\",\"type\":\"CREDIT_CARD\","
                        + "\"status\":\"OK\",\"logo\":\"https://resources.directa24.com/cashin/payment_method/square/VI.svg\"}]")));

      stubFor(get(urlEqualTo("/v3/payment_methods?country=BR"))
            .withHeader("Authorization", equalTo("Bearer " + READ_ONLY_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody("[{\"country\":\"BR\",\"code\":\"BB\","
                        + "\"name\":\"Banco Brasil\",\"type\":\"BANK_TRANSFER\",\"status\":\"OK\",\"logo\":\"https://resources.directa24"
                        + ".com/cashin/payment_method/square/BB.svg\"}]")));

      stubFor(get(urlEqualTo("/v3/payment_methods?country=AR"))
            .withHeader("Authorization", equalTo("Bearer " + READ_ONLY_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("[]")));
   }

   @Test
   public void allPaymentMethodsTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(READ_ONLY_KEY);

      PaymentMethodRequest paymentMethodRequest = PaymentMethodRequest.builder().build();

      List<PaymentMethodResponse> paymentMethodResponse = directa24Test.client.paymentMethods(paymentMethodRequest);

      assertTrue(paymentMethodResponse.size() == 2);

      verify(getRequestedFor(urlEqualTo("/v3/payment_methods")).withHeader("Content-Type", equalTo("application/json")));
   }

   @Test
   public void paymentMethodsBRTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(READ_ONLY_KEY);

      PaymentMethodRequest paymentMethodRequest = PaymentMethodRequest.builder() //
                                                                      .country("BR") //
                                                                      .build();

      List<PaymentMethodResponse> paymentMethodResponse = directa24Test.client.paymentMethods(paymentMethodRequest);

      assertTrue(paymentMethodResponse.size() == 1);

      verify(getRequestedFor(urlEqualTo("/v3/payment_methods?country=BR")).withHeader("Content-Type", equalTo("application/json")));
   }

   @Test
   public void emptyPaymentMethodsTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(READ_ONLY_KEY);

      PaymentMethodRequest paymentMethodRequest = PaymentMethodRequest.builder() //
                                                                      .country("AR") //
                                                                      .build();

      List<PaymentMethodResponse> paymentMethodResponse = directa24Test.client.paymentMethods(paymentMethodRequest);

      assertTrue(paymentMethodResponse.size() == 0);

      verify(getRequestedFor(urlEqualTo("/v3/payment_methods?country=AR")).withHeader("Content-Type", equalTo("application/json")));
   }

}
