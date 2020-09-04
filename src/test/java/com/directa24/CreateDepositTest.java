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
import com.directa24.model.internal.Address;
import com.directa24.model.internal.BankAccount;
import com.directa24.model.internal.Payer;
import com.directa24.model.request.CreateDepositRequest;
import com.directa24.model.response.CreateDepositResponse;

public class CreateDepositTest extends AbstractDirecta24Test {

   @Before
   public void createMocks() {

      stubFor(post(urlMatching("/v3/deposits"))
            .withHeader("X-Login", equalTo(DEPOSIT_KEY))
            .withHeader("Content-Type", equalTo("application/json"))
            .willReturn(aResponse()
                  .withStatus(200)
                  .withHeader("Content-Type", "application/json")
                  .withBody("{\"checkout_type\": \"ONE_SHOT\",\"iframe\": false,"
                        + "\"redirect_url\": \"https://checkout.astropay.com/v1/gateway/show?id_payment=169123506&signature=4ca838f2e6bbc8dce150\","
                        + "\"deposit_id\": 999,\"user_id\": \"4-9934519\"," + "\"merchant_invoice_id\": \"postmanTest395373786\",\"payment_info\": {"
                        + "\"type\": \"BANK_TRANSFER\",\"payment_method\": \"BB\","
                        + "\"payment_method_name\": \"Banco Brasil\",\"amount\": 19.95,\"currency\": \"BRL\","
                        + "\"expiration_date\": \"2020-06-10 20:36:31\",\"created_at\": \"2020-06-18 15:54:23\","
                        + "\"metadata\": {\"bar_code\": \"iVBORw0KGgoAAAANSUhEUgAAAioAAABkAQMAAACSM4nFAAAABlBMVEX///8AAABVwtN"
                        + "+AAAAAXRSTlMAQObYZgAAAGlJREFUWIXtzLEJwDAMRFGDW4FWMaQ90OoGD+BVBGpdxJkh7b/qc8Vr5Wu72wnZybXlkjKzVsTuoa/udtn9q"
                        + "/ebUmgsLw0Ll82MZzYYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGBgYGJh/zAsnuR9clZ3m8QAAAABJRU5ErkJggg==\","
                        + "\"digitable_line\": \"00190.00009 02860.239025 00278.269170 5 82730000001995\"}}}")));

   }

   @Test
   public void createDepositTest() throws Directa24Exception {

      Directa24 directa24Test = new Directa24.Test(DEPOSIT_KEY, SECRET_KEY);

      Address address = Address.builder() //
                               .street("Rua Dr. Franco Ribeiro, 52") //
                               .city("Rio Branco") //
                               .state("AC") //
                               .zipCode("11600-234") //
                               .build();

      Payer payer = Payer
            .builder()
            .id("4-9934519")
            .address(address)
            .document("21329039050")
            .documentType("CPF")
            .email("juanCarlos@hotmail.com")
            .firstName("Ricardo")
            .lastName("Carlos")
            .phone("+598 99730878")
            .build();

      BankAccount bankAccount = BankAccount
            .builder()
            .bankCode("01")
            .accountNumber("3242342")
            .accountType("SAVING")
            .beneficiary("Ricardo Carlos")
            .branch("12")
            .build();

      CreateDepositRequest createDepositRequest = CreateDepositRequest
            .builder()
            .invoiceId("999")
            .amount(new BigDecimal(100))
            .country("BR")
            .currency("BRL")
            .payer(payer)
            .paymentMethod("BB")
            .paymentType("BANK_TRANSFER")
            .bankAccount(bankAccount)
            .earlyRelease(false)
            .feeOnPayer(false)
            .surchargeOnPayer(false)
            .bonusAmount(BigDecimal.ONE)
            .bonusRelative(false)
            .strikethroughPrice(BigDecimal.ONE)
            .description("Test")
            .clientIp("186.51.171.84")
            .language("es")
            .deviceId("00000000-00000000-01234567-89ABCDEF")
            .backUrl("https://yoursite.com/deposit/108/cancel")
            .successUrl("https://yoursite.com/deposit/108/confirm")
            .errorUrl("https://yoursite.com/deposit/108/error")
            .notificationUrl("https://yoursite.com/ipn")
            .logo("https://yoursite.com/logo.png")
            .test(true)
            .mobile(false)
            .idempotency("")
            .build();

      CreateDepositResponse createDepositResponse = directa24Test.client.createDeposit(createDepositRequest);

      assertTrue(createDepositResponse != null);

      verify(postRequestedFor(urlEqualTo("/v3/deposits")).withHeader("Content-Type", equalTo("application/json")));
   }

}
