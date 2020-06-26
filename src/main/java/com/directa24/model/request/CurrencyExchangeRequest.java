package com.directa24.model.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CurrencyExchangeRequest implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String country;

   @JsonProperty
   private BigDecimal amount;

}
