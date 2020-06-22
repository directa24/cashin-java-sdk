package com.directa24.model.payer;

import java.io.Serializable;

import com.directa24.model.address.Address;

import lombok.Data;

@Data
public class Payer implements Serializable {

   private String id;

   private String document;

   private String documentType;

   private String email;

   private String firstName;

   private String lastName;

   private Address address;

   private String phone;

}
