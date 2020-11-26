package com.mpearsall.hr.entity.secondary.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@Embeddable
@Data
public class EmployeeWeek {
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "start", column = @Column(name = "monday_start")),
      @AttributeOverride(name = "end", column = @Column(name = "monday_end"))
  })
  private EmployeeDay monday;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "start", column = @Column(name = "tuesday_start")),
      @AttributeOverride(name = "end", column = @Column(name = "tuesday_end"))
  })
  private EmployeeDay tuesday;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "start", column = @Column(name = "wednesday_start")),
      @AttributeOverride(name = "end", column = @Column(name = "wednesday_end"))
  })
  private EmployeeDay wednesday;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "start", column = @Column(name = "thursday_start")),
      @AttributeOverride(name = "end", column = @Column(name = "thursday_end"))
  })
  private EmployeeDay thursday;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "start", column = @Column(name = "friday_start")),
      @AttributeOverride(name = "end", column = @Column(name = "friday_end"))
  })
  private EmployeeDay friday;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "start", column = @Column(name = "saturday_start")),
      @AttributeOverride(name = "end", column = @Column(name = "saturday_end"))
  })
  private EmployeeDay saturday;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "start", column = @Column(name = "sunday_start")),
      @AttributeOverride(name = "end", column = @Column(name = "sunday_end"))
  })
  private EmployeeDay sunday;

  public EmployeeWeek() {
    this.monday = new EmployeeDay();
    this.tuesday = new EmployeeDay();
    this.wednesday = new EmployeeDay();
    this.thursday = new EmployeeDay();
    this.friday = new EmployeeDay();
  }

  @JsonIgnore
  public static Map<DayOfWeek, EmployeeDay> getDayOfWeekMap(EmployeeWeek employeeWeek) {
    final Map<DayOfWeek, EmployeeDay> employeeDayMap = new HashMap<>();
    employeeDayMap.put(DayOfWeek.MONDAY, employeeWeek.getMonday());
    employeeDayMap.put(DayOfWeek.TUESDAY, employeeWeek.getTuesday());
    employeeDayMap.put(DayOfWeek.WEDNESDAY, employeeWeek.getWednesday());
    employeeDayMap.put(DayOfWeek.THURSDAY, employeeWeek.getThursday());
    employeeDayMap.put(DayOfWeek.FRIDAY, employeeWeek.getFriday());
    employeeDayMap.put(DayOfWeek.SATURDAY, employeeWeek.getSaturday());
    employeeDayMap.put(DayOfWeek.SUNDAY, employeeWeek.getSunday());

    return employeeDayMap;
  }
}
