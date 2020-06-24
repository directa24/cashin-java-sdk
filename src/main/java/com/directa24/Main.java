package com.directa24;

import java.math.BigDecimal;

import com.directa24.exception.Directa24Exception;
import com.directa24.model.internal.Address;
import com.directa24.model.internal.BankAccount;
import com.directa24.model.internal.Payer;
import com.directa24.model.request.CreateDepositRequest;
import com.directa24.model.response.CreateDepositResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

   public static void main(String[] args) {
      String loginSbx = "1955f2d";
      String secretKeySbx = "4lph0ns3";

      Directa24 directa24Sandbox = new Directa24.Sandbox(loginSbx, secretKeySbx, 30, 30);

      //      String loginProd = "loginProd";
      //      String secretKeyProd = "secretKeyProd";
      //
      //      Directa24 directa24Production = new Directa24.Production(loginProd, secretKeyProd);

      //      DepositStatusRequest depositStatusRequest = new DepositStatusRequest();
      //      depositStatusRequest.setId(300000001);
      //
      //      try {
      //         DepositStatusResponse depositStatusResponse = directa24Sandbox.client.depositStatus(depositStatusRequest);
      //         System.out.println(new ObjectMapper().writeValueAsString(depositStatusResponse));
      //      } catch (Directa24Exception | JsonProcessingException e) {
      //         e.printStackTrace();
      //      }

      //      try {
      //
      //         Address address = Address.builder()
      //                                  .street("Calle 13")
      //                                  .city("Montevideo")
      //                                  .state("AC")
      //                                  .zipCode("11600-234")
      //                                  .build();
      //
      //         Payer payer = Payer.builder()
      //                            .id("4-9934519")
      //                            .address(address)
      //                            .document("21329039050")
      //                            .documentType("CPF")
      //                            .email("juanCarlos@hotmail.com")
      //                            .firstName("Ricardo")
      //                            .lastName("Carlos")
      //                            .phone("+598 99730878")
      //                            .build();
      //
      //         CreateDepositRequest createDepositRequest = CreateDepositRequest.builder()
      //                                                                         .invoiceId("100")
      //                                                                         .amount(new BigDecimal(100))
      //                                                                         .country("BR")
      //                                                                         .currency("BRL")
      //                                                                         .payer(payer)
      //                                                                         .build();
      //
      //         CreateDepositResponse createDepositResponse = directa24Sandbox.client.createDeposit(createDepositRequest);
      //         System.out.println(new ObjectMapper().writeValueAsString(createDepositResponse));
      //      } catch (Directa24Exception | JsonProcessingException e) {
      //         e.printStackTrace();
      //      }

      try {

         Address address = Address.builder() //
                                  .street("Calle 13") //
                                  .city("Montevideo") //
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
               .invoiceId("108")
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
               .backUrl("")
               .successUrl("")
               .errorUrl("")
               .notificationUrl("")
               .logo("")
               .test(true)
               .mobile(false)
               .idempotency("")
               .build();

         CreateDepositResponse createDepositResponse = directa24Sandbox.client.createDeposit(createDepositRequest);
         System.out.println(new ObjectMapper().writeValueAsString(createDepositResponse));
      } catch (Directa24Exception | JsonProcessingException e) {
         e.printStackTrace();
      }

   }

}
