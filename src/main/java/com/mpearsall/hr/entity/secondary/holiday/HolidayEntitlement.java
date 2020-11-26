package com.mpearsall.hr.entity.secondary.holiday;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpearsall.hr.entity.secondary.CustomAbstractAuditable;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "company_year_id"}))
public class HolidayEntitlement extends CustomAbstractAuditable<Long> {
  @ManyToOne
  @ToString.Exclude
  @JsonIgnore
  private Employee employee;

  @NotNull
  @OneToOne
  private CompanyYear companyYear;

  @NotNull
  @DecimalMin("0.0")
  @DecimalMax("8784") // max hours in a year
  private double holidayEntitlementHours = 0.0;

  @JsonIgnore
  public Employee getEmployee() {
    return employee;
  }

  @JsonProperty
  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
}
