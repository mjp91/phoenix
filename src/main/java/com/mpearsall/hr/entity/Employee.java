package com.mpearsall.hr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Employee extends AbstractAuditable<User, Long> {
  @NotNull
  @OneToOne
  private User user;

  @JsonBackReference
  @OneToMany(mappedBy = "manager")
  private Collection<Employee> manages = new HashSet<>();

  @JsonManagedReference
  @ManyToOne
  @ToString.Exclude
  private Employee manager;

  @Valid
  @Embedded
  private EmployeeWeek employeeWeek;

  @JsonBackReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
  private Collection<HolidayEntitlement> holidayEntitlements = new HashSet<>();

  @JsonBackReference
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
  private Collection<Holiday> holidays = new HashSet<>();

  public void setManages(Collection<Employee> manages) {
    for (Employee employee : manages) {
      employee.setManager(this);
    }

    this.manages = manages;
  }

  public void setHolidayEntitlements(Collection<HolidayEntitlement> holidayEntitlements) {
    for (HolidayEntitlement entitlement : holidayEntitlements) {
      entitlement.setEmployee(this);
    }

    this.holidayEntitlements = holidayEntitlements;
  }
}
