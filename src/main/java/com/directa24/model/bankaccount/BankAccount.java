package com.directa24.model.bankaccount;

import java.io.Serializable;

import lombok.Data;

@Data
public class BankAccount implements Serializable {

   private String beneficiary;

   private String bankCode;

   private String branch;

   private String accountNumber;

   private String accountType;

}
