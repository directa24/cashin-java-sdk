package com.directa24.model.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentMethodResponse implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String country;

   @JsonProperty
   private String code;

   @JsonProperty
   private String name;

   @JsonProperty
   private String type;

   @JsonProperty
   private String status;

   @JsonProperty
   private String logo;

   @JsonProperty
   private Integer dailyAverage;

   @JsonProperty
   private Integer monthlyAverage;

}
