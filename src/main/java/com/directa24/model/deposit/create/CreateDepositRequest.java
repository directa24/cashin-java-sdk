package com.directa24.model.deposit.create;

import java.io.Serializable;
import java.math.BigDecimal;

import com.directa24.model.bankaccount.BankAccount;
import com.directa24.model.merchant.Merchant;
import com.directa24.model.payer.Payer;

import lombok.Data;

@Data
public class CreateDepositRequest implements Serializable {

   private String invoiceId;

   private BigDecimal amount;

   private String currency;

   private String country;

   private Payer payer;

   private String paymentMethod;

   private String paymentType;

   private BankAccount bankAccount;

   private boolean earlyRelease;

   private boolean feeOnPayer;

   private boolean surchargeOnPayer;

   private BigDecimal bonusAmount;

   private boolean bonusRelative;

   private BigDecimal strikethroughPrice;

   private String description;

   private String clientIp;

   private String deviceId;

   private String language;

   private String backUrl;

   private String successUrl;

   private String errorUrl;

   private String notificationUrl;

   private String logo;

   private boolean test;

   private boolean mobile;

   private Merchant merchant;

   private String idempotency;

}
