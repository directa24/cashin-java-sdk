package com.directa24.model.deposit.status;

import java.io.Serializable;
import java.math.BigDecimal;

public class DepositStatusResponse implements Serializable {

   private String userId;

   private Integer depositId;

   private String invoiceId;

   private String currency;

   private BigDecimal amount;

   private BigDecimal bonusAmount;

   private String paymentType;

   private String status;

}
