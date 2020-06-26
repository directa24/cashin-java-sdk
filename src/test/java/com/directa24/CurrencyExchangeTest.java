package com.directa24;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.directa24.base.AbstractDirecta24Test;
import com.directa24.exception.Directa24Exception;
import com.directa24.model.request.CurrencyExchangeRequest;
import com.directa24.model.response.CurrencyExchangeResponse;

public class CurrencyExchangeTest extends AbstractDirecta24Test {

   @Before
   public void createMocks() {
      stubFor(get(urlEqualTo("/v3/currency_exchange?country=BR"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Authorization", equalTo("Bearer " + API_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody("{\"fx_rate\":5.4272,\"currency\":\"BRL\",\"converted_amount\":5.4272}")));

      stubFor(get(urlEqualTo("/v3/currency_exchange?country=BR&amount=10"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Authorization", equalTo("Bearer " + API_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody("{\"fx_rate\":5.4272,\"currency\":\"BRL\",\"converted_amount\":54.2720}")));
   }

   @Test
   public void currencyExchangeBRTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, API_KEY, SECRET_KEY);

      CurrencyExchangeRequest currencyExchangeRequest = CurrencyExchangeRequest.builder() //
                                                                               .country("BR") //
                                                                               .build();

      CurrencyExchangeResponse currencyExchangeResponse = directa24Test.client.currencyExchange(currencyExchangeRequest);

      assertTrue(currencyExchangeResponse != null);

      verify(getRequestedFor(urlEqualTo("/v3/currency_exchange?country=BR")).withHeader("Content-Type", equalTo("application/json")));
   }

   @Test
   public void currencyExchangeAmountBRTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, API_KEY, SECRET_KEY);

      CurrencyExchangeRequest currencyExchangeRequest = CurrencyExchangeRequest.builder() //
                                                                               .country("BR") //
                                                                               .amount(BigDecimal.TEN) //
                                                                               .build();

      CurrencyExchangeResponse currencyExchangeResponse = directa24Test.client.currencyExchange(currencyExchangeRequest);

      assertTrue(currencyExchangeResponse != null);

      verify(getRequestedFor(urlEqualTo("/v3/currency_exchange?country=BR&amount=10")).withHeader("Content-Type", equalTo("application/json")));
   }

}
