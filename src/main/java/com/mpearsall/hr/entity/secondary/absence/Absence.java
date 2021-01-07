package com.mpearsall.hr.entity.secondary.absence;

import com.mpearsall.hr.entity.secondary.Attachment;
import com.mpearsall.hr.entity.secondary.CustomAbstractAuditable;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Absence extends CustomAbstractAuditable<Long> {
  @NotNull
  @ManyToOne
  private Employee employee;

  @NotNull
  @ManyToOne
  private CompanyYear companyYear;

  @NotNull
  @Column(columnDefinition = "text")
  private String reason;

  @NotNull
  private LocalDate start;

  @NotNull
  @Column(name = "\"end\"")
  private LocalDate end;

  private Boolean authorized;

  private String unauthorizeReason;

  private boolean cancelled = false;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Attachment> attachments;
}
