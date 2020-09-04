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
import com.directa24.model.request.BankDataRequest;
import com.directa24.model.response.BankDataResponse;

public class BanksDataTest extends AbstractDirecta24Test {

   @Before
   public void createMocks() {
      stubFor(get(urlEqualTo("/v3/banks?country=BR"))
            .withHeader("Authorization", equalTo("Bearer " + READ_ONLY_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody("[" + "{\"code\": 1,\"name\": \"BANCO DO BRASIL S.A.\"},{\"code\": 3,\"name\": \"BANCO DA AMAZONIA S.A.\"},"
                        + "{\"code\": 4,\"name\": \"BANCO DO NORDESTE DO BRASIL S.A.\"}]")));

      stubFor(get(urlEqualTo("/v3/banks?country=AR"))
            .withHeader("Authorization", equalTo("Bearer " + READ_ONLY_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("[]")));
   }

   @Test
   public void banksBRTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(READ_ONLY_KEY);

      BankDataRequest bankDataRequest = BankDataRequest.builder() //
                                                       .country("BR") //
                                                       .build();

      List<BankDataResponse> bankDataResponses = directa24Test.client.getBanks(bankDataRequest);

      assertTrue(bankDataResponses.size() == 3);

      verify(getRequestedFor(urlEqualTo("/v3/banks?country=BR")).withHeader("Content-Type", equalTo("application/json")));
   }

   @Test
   public void emptyBanksTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(READ_ONLY_KEY);

      BankDataRequest bankDataRequest = BankDataRequest.builder() //
                                                       .country("AR") //
                                                       .build();

      List<BankDataResponse> bankDataResponses = directa24Test.client.getBanks(bankDataRequest);

      assertTrue(bankDataResponses.size() == 0);

      verify(getRequestedFor(urlEqualTo("/v3/banks?country=AR")).withHeader("Content-Type", equalTo("application/json")));
   }

}
