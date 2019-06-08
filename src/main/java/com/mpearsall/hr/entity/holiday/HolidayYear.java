package com.mpearsall.hr.entity.holiday;

import com.mpearsall.hr.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HolidayYear extends AbstractAuditable<User, Long> {
  @NotNull
  private String name;

  @NotNull
  private LocalDate yearStart;

  @NotNull
  private LocalDate yearEnd;
}
