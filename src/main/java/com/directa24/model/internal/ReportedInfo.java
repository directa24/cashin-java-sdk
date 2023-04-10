package com.directa24.model.internal;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReportedInfo implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private String bankAccountNumber;

   @JsonProperty
   private String bankBranch;

   @JsonProperty
   private String bankBeneficiary;

   @JsonProperty
   private String bankAccountType;

}
