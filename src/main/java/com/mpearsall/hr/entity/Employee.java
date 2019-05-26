package com.mpearsall.hr.entity;

import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Employee extends AbstractAuditable<User, Long> {
  @NotNull
  @OneToOne
  private User user;

  @OneToMany(mappedBy = "manager")
  private Collection<Employee> manages;

  @ManyToOne
  private Employee manager;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
  private Collection<HolidayEntitlement> holidayEntitlements;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
  private Collection<Holiday> holidays;

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
