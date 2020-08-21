package com.directa24.model.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.directa24.model.internal.BankAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateRefundRequest implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private Integer depositId;

   @JsonProperty
   private String invoiceId;

   @JsonProperty
   private BigDecimal amount;

   @JsonProperty
   private BankAccount bankAccount;

   @JsonProperty
   private String comments;

   @JsonProperty
   private String notificationUrl;

   @JsonProperty
   private String country;

   @JsonProperty
   private String origin;

   @JsonIgnore
   private String idempotency;

}
