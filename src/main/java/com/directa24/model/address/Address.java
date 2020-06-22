package com.directa24.model.address;

import java.io.Serializable;

import lombok.Data;

@Data
public class Address implements Serializable {

   private String street;

   private String city;

   private String state;

   private String zipCode;

}
