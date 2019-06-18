package com.mpearsall.hr.entity.holiday;

import com.mpearsall.hr.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HolidayDate extends AbstractAuditable<User, Long> {
  @NotNull
  private LocalDate date;

  @NotNull
  @Enumerated(EnumType.STRING)
  private HolidayPeriod holidayPeriod = HolidayPeriod.ALL_DAY;
}
