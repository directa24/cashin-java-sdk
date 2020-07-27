package com.directa24.model.internal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentInfo implements Serializable {

   private static final long serialVersionUID = 1L;

   public static final String DATE_TIME_CUSTOM_PATTERN = "yyyy-MM-dd HH:mm:ss";

   @JsonProperty
   private String type;

   @JsonProperty
   private String paymentMethod;

   @JsonProperty
   private String paymentMethodName;

   @JsonProperty
   private BigDecimal amount;

   @JsonProperty
   private String currency;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_CUSTOM_PATTERN)
   private LocalDateTime expirationDate;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_CUSTOM_PATTERN)
   private LocalDateTime createdAt;

   @JsonProperty
   private Map<String, Object> metadata;

}
