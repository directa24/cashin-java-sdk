package com.directa24.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RefundStatusResponse implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private Integer depositId;

   @JsonProperty
   private String merchantInvoiceId;

   @JsonProperty
   private String status;

   @JsonProperty
   private BigDecimal amount;

}
