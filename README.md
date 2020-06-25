# Directa 24 Java client library

The official [Directa 24][directa24] Java client library.

## Installation

### Requirements

- Java 1.8 or later

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "com.directa24:cashin-java-sdk:1.0.0"
```

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.directa24</groupId>
  <artifactId>cashin-java-sdk</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Documentation

Please see the [Java API docs][api-docs] for the most up-to-date documentation.

## Usage

#### Credentials

```java
String depositKeySbx = "1955f2d";
String apiKeySbx = "eJ3Ldt6Xma";
String secretKeySbx = "4lph0ns3";
int connectTimeout = 30; 
int readTimeout = 30;

Directa24 directa24Sandbox = new Directa24.Sandbox(depositKeySbx, apiKeySbx, secretKeySbx, connectTimeout, readTimeout);
```

#### Create Deposit

```java
public class CreateDepositExample {

   public static void main(String[] args) {

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

      try {
         CreateDepositResponse createDepositResponse = directa24Sandbox.client.createDeposit(createDepositRequest);
      } catch (Directa24Exception e) {
         // Manage errors
      }
   }
}
```
#### Deposit Status

```java
public class DepositStatusExample {

   public static void main(String[] args) {
      DepositStatusRequest depositStatusRequest = DepositStatusRequest.builder().id(300000001).build();
      try {
         DepositStatusResponse depositStatusResponse = directa24Sandbox.client.depositStatus(depositStatusRequest);
      } catch (Directa24Exception e) {
         // Manage errors
      }
   }
}
```

#### Payment Methods

```java
public class PaymentMethodsExample {

   public static void main(String[] args) {
      PaymentMethodRequest paymentMethodRequest = PaymentMethodRequest.builder().country("BR").build();
      try {
         List<PaymentMethodResponse> paymentMethodResponse = directa24Sandbox.client.paymentMethods(paymentMethodRequest);
      } catch (Directa24Exception e) {
         // Manage errors
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
[api-docs]: https://directa24.com
[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview
[junit]: https://junit.org/junit4/
[wiremock]: http://wiremock.org 