package com.directa24.model.internal;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Merchant implements Serializable {

   private static final long serialVersionUID = 1L;

   private Integer idmerchants;

   private String xLogin;

   private String xTranKey;

   private String merchantName;

   private String legalName;

   private String active;

   private Integer test;

   private String secretkey;

   private String xLogin1;

   private String xTrans;

   private String logo;

   private String confirmationUrl;

   private String returnUrl;

   private Integer businessType;

   private Integer highRiskøø;

   private String ccDescriptor;

   private Integer cookieControl;

   private String routingType;

   private String balanceCurrency;

}
