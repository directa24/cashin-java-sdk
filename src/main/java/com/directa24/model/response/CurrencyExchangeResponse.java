package com.directa24.model.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CurrencyExchangeResponse implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private BigDecimal fxRate;

   @JsonProperty
   private String currency;

   @JsonProperty
   private BigDecimal convertedAmount;

}
