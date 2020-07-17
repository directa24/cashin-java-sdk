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
import com.directa24.model.request.ExchangeRateRequest;
import com.directa24.model.response.ExchangeRateResponse;

public class ExchangeRatesTest extends AbstractDirecta24Test {

   @Before
   public void createMocks() {
      stubFor(get(urlEqualTo("/v3/exchange_rates?country=BR"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Authorization", equalTo("Bearer " + API_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody("{\"fx_rate\":5.4272,\"currency\":\"BRL\",\"converted_amount\":5.4272}")));

      stubFor(get(urlEqualTo("/v3/exchange_rates?country=BR&amount=10"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Authorization", equalTo("Bearer " + API_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody("{\"fx_rate\":5.4272,\"currency\":\"BRL\",\"converted_amount\":54.2720}")));
   }

   @Test
   public void exchangeRatesBRTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, API_KEY, SECRET_KEY);

      ExchangeRateRequest exchangeRatesRequest = ExchangeRateRequest.builder() //
                                                                    .country("BR") //
                                                                    .build();

      ExchangeRateResponse exchangeRateResponse = directa24Test.client.exchangeRates(exchangeRatesRequest);

      assertTrue(exchangeRateResponse != null);

      verify(getRequestedFor(urlEqualTo("/v3/exchange_rates?country=BR")).withHeader("Content-Type", equalTo("application/json")));
   }

   @Test
   public void exchangeRatesAmountBRTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, API_KEY, SECRET_KEY);

      ExchangeRateRequest exchangeRatesRequest = ExchangeRateRequest.builder() //
                                                                    .country("BR") //
                                                                    .amount(BigDecimal.TEN) //
                                                                    .build();

      ExchangeRateResponse exchangeRateResponse = directa24Test.client.exchangeRates(exchangeRatesRequest);

      assertTrue(exchangeRateResponse != null);

      verify(getRequestedFor(urlEqualTo("/v3/exchange_rates?country=BR&amount=10")).withHeader("Content-Type", equalTo("application/json")));
   }

}
