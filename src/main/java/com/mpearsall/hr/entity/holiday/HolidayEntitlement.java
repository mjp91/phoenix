package com.mpearsall.hr.entity.holiday;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HolidayEntitlement extends AbstractAuditable<User, Long> {
  @ManyToOne
  private Employee employee;

  @NotNull
  @OneToOne
  private HolidayYear holidayYear;

  @NotNull
  @Min(0)
  @Max(8784) // max hours in a year
  private Integer holidayEntitlementHours = 0;
}
