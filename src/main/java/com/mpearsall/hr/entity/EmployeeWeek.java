package com.mpearsall.hr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Data
@NoArgsConstructor
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
      @AttributeOverride(name = "start", column = @Column(name = "wedneday_start")),
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
}
