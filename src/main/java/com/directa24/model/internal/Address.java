package com.directa24.model.internal;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Address implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String street;

   @JsonProperty
   private String city;

   @JsonProperty
   private String state;

   @JsonProperty
   private String zipCode;

}
