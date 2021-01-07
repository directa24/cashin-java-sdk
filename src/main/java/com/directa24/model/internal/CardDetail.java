package com.directa24.model.internal;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CardDetail implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String cardHolder;

   @JsonProperty
   private String maskedCard;

   @JsonProperty
   private String brand;

   @JsonProperty
   private String expiration;

}
