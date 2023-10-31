package com.directa24.model.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.directa24.model.internal.BankAccount;
import com.directa24.model.internal.Crypto;
import com.directa24.model.internal.Payer;
import com.directa24.model.internal.ReportedInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateDepositRequest implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String invoiceId;

   @JsonProperty
   private BigDecimal amount;

   @JsonProperty
   private String currency;

   @JsonProperty
   private String country;

   @JsonProperty
   private Payer payer;

   @JsonProperty
   private String paymentMethod;

   @JsonProperty
   private String paymentType;

   @JsonProperty
   private List<String> paymentTypes;

   @JsonProperty
   private BankAccount bankAccount;

   @JsonProperty
   private ReportedInfo reportedInfo;

   @JsonProperty
   private boolean earlyRelease;

   @JsonProperty
   private boolean feeOnPayer;

   @JsonProperty
   private boolean surchargeOnPayer;

   @JsonProperty
   private BigDecimal bonusAmount;

   @JsonProperty
   private boolean bonusRelative;

   @JsonProperty
   private BigDecimal strikethroughPrice;

   @JsonProperty
   private String description;

   @JsonProperty
   private String clientIp;

   @JsonProperty
   private String deviceId;

   @JsonProperty
   private String language;

   @JsonProperty
   private String backUrl;

   @JsonProperty
   private String successUrl;

   @JsonProperty
   private String errorUrl;

   @JsonProperty
   private String notificationUrl;

   @JsonProperty
   private String logo;

   @JsonProperty
   private boolean test;

   @JsonProperty
   private boolean mobile;

   @JsonProperty
   private boolean requestPayerDataOnValidationFailure;

   @JsonProperty
   private Crypto crypto;

   @JsonIgnore
   private String idempotency;

   @JsonProperty
   private boolean allowInstallments;

   @JsonProperty
   private Integer expiration;

}
