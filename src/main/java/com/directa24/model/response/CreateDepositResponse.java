package com.directa24.model.response;

import java.io.Serializable;

import com.directa24.model.internal.PaymentInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateDepositResponse implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String checkoutType;

   @JsonProperty
   private String redirectUrl;

   @JsonProperty
   private Integer depositId;

   @JsonProperty
   private String userId;

   @JsonProperty
   private String merchantInvoiceId;

   @JsonProperty
   private PaymentInfo paymentInfo;

}
