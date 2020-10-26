package com.directa24.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.directa24.model.internal.CardDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
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
   private String country;

   @JsonProperty
   private String currency;

   @JsonProperty
   private BigDecimal usdAmount;

   @JsonProperty
   private BigDecimal localAmount;

   @JsonProperty
   private BigDecimal bonusAmount;

   @JsonProperty
   private Boolean bonusRelative;

   @JsonProperty
   private String paymentMethod;

   @JsonProperty
   private String paymentType;

   @JsonProperty
   private String status;

   @JsonProperty
   private CardDetail cardDetail;

}
