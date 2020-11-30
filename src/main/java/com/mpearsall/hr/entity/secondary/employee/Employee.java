package com.mpearsall.hr.entity.secondary.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.mpearsall.hr.entity.secondary.CustomAbstractAuditable;
import com.mpearsall.hr.entity.secondary.Department;
import com.mpearsall.hr.entity.secondary.holiday.Holiday;
import com.mpearsall.hr.entity.secondary.holiday.HolidayEntitlement;
import com.mpearsall.hr.view.View;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
public class Employee extends CustomAbstractAuditable<Long> {
  @NotNull
  @Column(name = "\"user\"")
  private Long user;

  @ManyToOne
  private Department department;

  private String reference;

  @ManyToOne
  private JobRole jobRole;

  @Valid
  @Embedded
  @JsonView(View.Admin.class)
  private Address address = new Address();

  @JsonView(View.Admin.class)
  private String telephoneNumber;

  @JsonView(View.Admin.class)
  private String mobileNumber;

  private String extensionNumber;

  private String profileFileName;

  private LocalDate dateOfBirth;

  private LocalDate serviceStartDate;

  private LocalDate serviceEndDate;

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
  @JsonView(View.Admin.class)
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

  public void addHolidayEntitlement(HolidayEntitlement holidayEntitlement) {
    holidayEntitlement.setEmployee(this);

    this.holidayEntitlements.add(holidayEntitlement);
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
