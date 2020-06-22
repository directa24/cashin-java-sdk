package com.directa24.model.deposit.status;

import java.io.Serializable;

import com.directa24.model.merchant.Merchant;

import lombok.Data;

@Data
public class DepositStatusRequest implements Serializable {

   private Integer id;

   private Merchant merchant;

}
