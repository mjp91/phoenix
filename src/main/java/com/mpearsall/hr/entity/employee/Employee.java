package com.mpearsall.hr.entity.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpearsall.hr.entity.Department;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import com.mpearsall.hr.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Employee extends AbstractAuditable<User, Long> {
  @NotNull
  @OneToOne
  private User user;

  @ManyToOne
  private Department department;

  private String reference;

  @ManyToOne
  private JobRole jobRole;

  @Valid
  @Embedded
  private Address address = new Address();

  private String telephoneNumber;

  private String mobileNumber;

  private String extensionNumber;

  private String profileFileName;

  @JsonIgnore
  @OneToMany(mappedBy = "manager")
  private Collection<Employee> manages = new HashSet<>();

  @ManyToOne
  @ToString.Exclude
  private Employee manager;

  @Valid
  @Embedded
  private EmployeeWeek employeeWeek = new EmployeeWeek();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
  private Collection<HolidayEntitlement> holidayEntitlements = new HashSet<>();

  @JsonIgnore
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

  public Address getAddress() {
    return address != null ? address : new Address();
  }

  @JsonIgnore
  public static Set<DayOfWeek> getDaysWorked(Employee employee) {
    return EmployeeWeek.getDayOfWeekMap(employee.getEmployeeWeek()).entrySet().stream()
        .filter((e) -> e.getValue() != null)
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }
}
