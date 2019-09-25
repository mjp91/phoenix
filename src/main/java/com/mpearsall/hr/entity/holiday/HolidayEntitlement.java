package com.mpearsall.hr.entity.holiday;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HolidayEntitlement extends AbstractAuditable<User, Long> {
  @ManyToOne
  @ToString.Exclude
  @JsonIgnore
  private Employee employee;

  @NotNull
  @OneToOne
  private HolidayYear holidayYear;

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
