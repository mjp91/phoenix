package com.mpearsall.hr.entity.holiday;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Holiday extends AbstractAuditable<User, Long> {
  @NotNull
  @ManyToOne
  private Employee employee;

  @NotNull
  @OneToOne
  private HolidayYear holidayYear;

  @NotNull
  private LocalDate date;

  @NotNull
  @Enumerated(EnumType.STRING)
  private HolidayPeriod holidayPeriod;

  @Basic
  private boolean approved = false;
}
