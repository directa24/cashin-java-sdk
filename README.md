# Directa 24 Java client library


The official [Directa 24][directa24] Java client library.

## Installation

### Requirements

- Java 1.8 or later

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "com.directa24:cashin-java-sdk:1.0.16"
```

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.directa24</groupId>
  <artifactId>cashin-java-sdk</artifactId>
  <version>1.0.16</version>
</dependency>
```

## Documentation

Please see the [Java API docs][api-docs] for the most up-to-date documentation.

## Usage

#### Deposit Credentials

Instructions on how to get your keys by clicking [here](https://docs.directa24.com/api-documentation/deposits-api/technical-and-security-aspects#api-keys)  

```java
String depositKeySbx = ${apiKey};
String secretKeySbx = ${apiSignature};

Directa24 directa24Sandbox = new Directa24.Sandbox(depositKeySbx, secretKeySbx);
```

#### Read Only Credentials

```java
String readOnlyKeySbx = ${readOnlyApiKey};

Directa24 directa24Sandbox = new Directa24.Sandbox(readOnlyKeySbx);
```

#### Create Deposit

```java
public class CreateDepositExample {

   public static void main(String[] args) {

      Address address = Address.builder()
                               .street("Rua Dr. Franco Ribeiro, 52")
                               .city("Rio Branco")
                               .state("AC")
                               .zipCode("11600-234")
                               .build();

      Payer payer = Payer
            .builder()
            .id("4-9934519")
            .address(address)
            .document("14057025162")
            .documentType("CPF")
            .email("john.smith@example.com")
            .firstName("John")
            .lastName("Smith")
            .phone("+551199999999")
            .build();

      BankAccount bankAccount = BankAccount
            .builder()
            .bankCode("01")
            .accountNumber("9999999")
            .accountType("SAVING")
            .beneficiary("John Smith")
            .branch("99")
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
            .backUrl("https://yoursite.com/deposit/108/cancel")
            .successUrl("https://yoursite.com/deposit/108/confirm")
            .errorUrl("https://yoursite.com/deposit/108/error")
            .notificationUrl("https://yoursite.com/ipn")
            .logo("https://yoursite.com/logo.png")
            .test(true)
            .mobile(false)
            .idempotency("")
            .build();

      try {
         CreateDepositResponse createDepositResponse = directa24Sandbox.client.createDeposit(createDepositRequest);
         
         // Handle response

      } catch (Directa24Exception e) {
         // Handle errors
      }
   }
}
```
#### Deposit Status

```java
public class DepositStatusExample {

   public static void main(String[] args) {
      DepositStatusRequest depositStatusRequest = DepositStatusRequest
                                                  .builder()
                                                  .id(300000001)
                                                  .build();
      try {
         DepositStatusResponse depositStatusResponse = directa24Sandbox.client.depositStatus(depositStatusRequest);
      
         // Handle response

      } catch (Directa24Exception e) {
         // Handle errors
      }
   }
}
```

#### Payment Methods

```java
public class PaymentMethodsExample {

   public static void main(String[] args) {
      PaymentMethodRequest paymentMethodRequest = PaymentMethodRequest
                                                  .builder()
                                                  .country("BR")
                                                  .build();
      try {
         List<PaymentMethodResponse> paymentMethodResponse = directa24Sandbox.client.paymentMethods(paymentMethodRequest);
      
         // Handle response

      } catch (Directa24Exception e) {
         // Handle errors
      }
   }
}
```

#### Exchange rates

```java
public class ExchangeRateExample {

   public static void main(String[] args) {
      ExchangeRateRequest exchangeRatesRequest = ExchangeRateRequest
                                                 .builder()
                                                 .country("BR")
                                                 .amount(BigDecimal.TEN)
                                                 .build();
      try {
         ExchangeRateResponse exchangeRateResponse = directa24Test.client.exchangeRates(exchangeRateRequest);
         
         // Handle response

      } catch (Directa24Exception e) {
         // Handle errors
      }
   }
}
```
For more examples see the bundled tests.

### Dependencies

The library uses [Project Lombok][lombok]. While it is not a requirement, you
might want to install a [plugin][lombok-plugins] for your favorite IDE to
facilitate development.

[JUnit 4][junit] and [Wiremock][wiremock] library are needed to run the bundled tests.

[directa24]: https://directa24.com
[api-docs]: https://docs.directa24.com/deposits-api
[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview
[junit]: https://junit.org/junit4/
[wiremock]: http://wiremock.org 