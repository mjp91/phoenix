package com.mpearsall.hr.entity.secondary.holiday;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mpearsall.hr.entity.secondary.CustomAbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = HolidayDate.class)
public class HolidayDate extends CustomAbstractAuditable<Long> implements Comparable<HolidayDate> {
  @ManyToOne
  @ToString.Exclude
  private Holiday holiday;

  @NotNull
  private LocalDate date;

  @NotNull
  @Enumerated(EnumType.STRING)
  private HolidayPeriod holidayPeriod = HolidayPeriod.ALL_DAY;

  @Override
  public int compareTo(HolidayDate holidayDate) {
    return this.date.compareTo(holidayDate.getDate());
  }
}
