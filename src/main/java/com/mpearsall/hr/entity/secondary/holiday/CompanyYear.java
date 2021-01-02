package com.mpearsall.hr.entity.secondary.holiday;

import com.mpearsall.hr.entity.secondary.CustomAbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CompanyYear extends CustomAbstractAuditable<Long> {
  @NotNull
  @Column(unique = true)
  private String name;

  @NotNull
  private LocalDate yearStart;

  @NotNull
  private LocalDate yearEnd;
}
