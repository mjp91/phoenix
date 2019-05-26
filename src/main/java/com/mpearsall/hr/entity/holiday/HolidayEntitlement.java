package com.mpearsall.hr.entity.holiday;

import com.mpearsall.hr.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class HolidayEntitlement {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

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
