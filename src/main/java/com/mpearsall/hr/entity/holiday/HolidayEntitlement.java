package com.mpearsall.hr.entity.holiday;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HolidayEntitlement extends AbstractAuditable<User, Long> {
  @JsonManagedReference
  @ManyToOne
  @ToString.Exclude
  private Employee employee;

  @NotNull
  @OneToOne
  private HolidayYear holidayYear;

  @NotNull
  @DecimalMin("0.0")
  @DecimalMax("8784") // max hours in a year
  private Double holidayEntitlementHours = 0.0;
}
