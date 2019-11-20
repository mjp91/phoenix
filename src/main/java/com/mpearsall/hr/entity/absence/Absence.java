package com.mpearsall.hr.entity.absence;

import com.mpearsall.hr.entity.Attachment;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.holiday.CompanyYear;
import com.mpearsall.hr.entity.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
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
public class Absence extends AbstractAuditable<User, Long> {
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
  private LocalDate end;

  private Boolean authorized;

  private boolean cancelled = false;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Attachment> attachments;
}
