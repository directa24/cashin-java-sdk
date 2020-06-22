package com.directa24;

import com.directa24.exception.Directa24Exception;
import com.directa24.model.deposit.status.DepositStatusRequest;
import com.directa24.model.merchant.Merchant;

public class Main {

   public static void main(String[] args) {
      String loginSbx = "loginSbx";
      String secretKeySbx = "secretKeySbx";

      Directa24 directa24Sandbox = new Directa24.Sandbox(loginSbx, secretKeySbx).connectTimeout(10).readTimeout(10);

//      String loginProd = "loginProd";
//      String secretKeyProd = "secretKeyProd";
//
//      Directa24 directa24Production = new Directa24.Production(loginProd, secretKeyProd);

      DepositStatusRequest depositStatusRequest = new DepositStatusRequest();
      depositStatusRequest.setId(12239078);

      Merchant merchant = new Merchant();
      merchant.setIdmerchants(8);
      merchant.setXLogin(loginSbx);
//      this.xTranKey = xTranKey;
//      this.merchantName = merchantName;
//      this.legalName = legalName;
//      this.active = active;
//      this.test = test;
      merchant.setSecretkey(secretKeySbx);
//      this.xLogin1 = xLogin1;
//      this.xTrans = xTrans;
//      this.logo = logo;
//      this.confirmationUrl = confirmationUrl;
//      this.returnUrl = returnUrl;
//      this.businessType = businessType;
//      this.highRiskøø = highRiskøø;
//      this.ccDescriptor = ccDescriptor;
//      this.cookieControl = cookieControl;
//      this.routingType = routingType;
//      this.balanceCurrency = balanceCurrency;

      depositStatusRequest.setMerchant(merchant);

      try {
         directa24Sandbox.client.depositStatus(depositStatusRequest);
      } catch (Directa24Exception e) {
         e.printStackTrace();
      }

   }

}
