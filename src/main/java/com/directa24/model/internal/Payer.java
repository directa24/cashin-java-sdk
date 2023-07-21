package com.directa24.model.internal;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Payer implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String id;

   @JsonProperty
   private String document;

   @JsonProperty
   private String documentType;

   @JsonProperty
   private String email;

   @JsonProperty
   private String firstName;

   @JsonProperty
   private String lastName;

   @JsonProperty
   private Address address;

   @JsonProperty
   private String phone;

   @JsonProperty
   private LocalDate birthDate;

   @JsonProperty
   private LocalDate registrationDate;

}
