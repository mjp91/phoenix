package com.mpearsall.hr.entity.secondary.employee;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
  private String line;
  private String city;
  private String province;
  private String postalCode;
  private Country country;
}
