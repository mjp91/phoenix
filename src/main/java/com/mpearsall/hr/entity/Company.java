package com.mpearsall.hr.entity;

import com.mpearsall.hr.entity.employee.EmployeeWeek;
import com.mpearsall.hr.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Company extends AbstractAuditable<User, Long> {
  @NotEmpty
  private String name;

  @Valid
  @Embedded
  private EmployeeWeek defaultEmployeeWeek = new EmployeeWeek();

  @NotNull
  @DecimalMin("0.0")
  @DecimalMax("8784") // max hours in a year
  private double defaultHolidayEntitlementHours = 0.0;
}
