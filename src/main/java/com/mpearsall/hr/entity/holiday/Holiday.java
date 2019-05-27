package com.mpearsall.hr.entity.holiday;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Holiday extends AbstractAuditable<User, Long> {
  private String name;

  @NotNull
  @ManyToOne
  private Employee employee;

  @NotNull
  @OneToOne
  private HolidayYear holidayYear;

  @NotEmpty
  @OneToMany(cascade = CascadeType.ALL)
  private Collection<HolidayDate> holidayDates;

  @Basic
  private boolean approved = false;
}
