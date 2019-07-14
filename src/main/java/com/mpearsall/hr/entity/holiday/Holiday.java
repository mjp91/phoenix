package com.mpearsall.hr.entity.holiday;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Holiday.class)
public class Holiday extends AbstractAuditable<User, Long> {
  private String name;

  @NotNull
  @ManyToOne
  @ToString.Exclude
  private Employee employee;

  @NotNull
  @OneToOne
  private HolidayYear holidayYear;

  @NotEmpty
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "holiday")
  private Collection<HolidayDate> holidayDates;

  private Boolean approved;

  private String disapprovalReason;

  @NotNull
  private boolean cancelled = false;

  public void setHolidayDates(Collection<HolidayDate> holidayDates) {
    if (holidayDates != null) {
      for (HolidayDate holidayDate : holidayDates) {
        holidayDate.setHoliday(this);
      }
    }

    this.holidayDates = holidayDates;
  }
}
