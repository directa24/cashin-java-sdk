package com.directa24.model.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.directa24.model.internal.CreditCard;
import com.directa24.model.internal.Payer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PCIDepositRequest implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private final String invoiceId;

   @JsonProperty
   private final BigDecimal amount;

   @JsonProperty
   private final String currency;

   @JsonProperty
   private final String country;

   @JsonProperty
   private final Payer payer;

   @JsonProperty
   private final String description;

   @JsonProperty
   private final String clientIp;

   @JsonProperty
   private final String deviceId;

   @JsonProperty
   private final boolean feeOnPayer;

   @JsonProperty
   private final CreditCard creditCard;

}
