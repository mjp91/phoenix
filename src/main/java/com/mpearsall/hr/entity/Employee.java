package com.mpearsall.hr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @OneToOne
  private User user;

  @OneToMany(mappedBy = "manager")
  private Collection<Employee> manages;

  @ManyToOne
  private Employee manager;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
  @MapKey(name = "year")
  private Map<Integer, HolidayEntitlement> holidayEntitlement;

  public void setManages(Collection<Employee> manages) {
    for (Employee employee : manages) {
      employee.setManager(this);
    }

    this.manages = manages;
  }

  public void setHolidayEntitlement(Map<Integer, HolidayEntitlement> holidayEntitlement) {
    for (HolidayEntitlement entitlement : holidayEntitlement.values()) {
      entitlement.setEmployee(this);
    }

    this.holidayEntitlement = holidayEntitlement;
  }
}
