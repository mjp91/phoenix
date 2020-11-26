package com.mpearsall.hr.entity.secondary.holiday;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mpearsall.hr.dto.DateRange;
import com.mpearsall.hr.entity.secondary.CustomAbstractAuditable;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Holiday.class)
public class Holiday extends CustomAbstractAuditable<Long> {
  private String name;

  @NotNull
  @ManyToOne
  @ToString.Exclude
  private Employee employee;

  @NotNull
  @OneToOne
  private CompanyYear companyYear;

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

  public static DateRange getHolidayRange(Holiday holiday) {
    DateRange result = null;

    final List<HolidayDate> holidayDates = new ArrayList<>(holiday.getHolidayDates());
    Collections.sort(holidayDates);

    if (!holidayDates.isEmpty()) {
      result = new DateRange(holidayDates.get(0).getDate(), holidayDates.get(holidayDates.size() - 1).getDate());
    }

    return result;
  }
}
