package com.mpearsall.hr.entity.secondary.employee;

import com.mpearsall.hr.entity.secondary.CustomAbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotEmpty;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class JobRole extends CustomAbstractAuditable<Long> {
  @NotEmpty
  private String description;
}
