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

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = HolidayEntitlement.class)
public class HolidayEntitlement extends AbstractAuditable<User, Long> {
  @ManyToOne
  @ToString.Exclude
  private Employee employee;

  @NotNull
  @OneToOne
  private HolidayYear holidayYear;

  @NotNull
  @DecimalMin("0.0")
  @DecimalMax("8784") // max hours in a year
  private Double holidayEntitlementHours = 0.0;
}
