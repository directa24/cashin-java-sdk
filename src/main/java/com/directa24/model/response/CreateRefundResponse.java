package com.directa24.model.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateRefundResponse implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonProperty
   private Integer refundId;

}
