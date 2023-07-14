package com.directa24.model.internal;

import static com.directa24.model.internal.PaymentInfo.DATE_TIME_CUSTOM_PATTERN;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class RefundInfo implements Serializable {

   private static final long serialVersionUID = 1L;

   private String type;

   private String result;

   private String reason;

   private String reasonCode;

   private String paymentMethod;

   private String paymentMethodName;

   private BigDecimal amount;

   private String currency;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_CUSTOM_PATTERN)
   private LocalDateTime createdAt;

}
