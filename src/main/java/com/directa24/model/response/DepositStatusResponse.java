package com.directa24.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DepositStatusResponse implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String userId;

   @JsonProperty
   private Integer depositId;

   @JsonProperty
   private String invoiceId;

   @JsonProperty
   private String currency;

   @JsonProperty
   private BigDecimal amount;

   @JsonProperty
   private BigDecimal bonusAmount;

   @JsonProperty
   private String paymentType;

   @JsonProperty
   private String status;

}
