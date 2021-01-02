package com.mpearsall.hr.entity.secondary;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class CustomAbstractAuditable<PK extends Serializable> extends AbstractPersistable<PK> implements Auditable<Long, PK, LocalDateTime> {
  private static final long serialVersionUID = 141481953116476081L;

  private Long createdBy;

  private LocalDateTime createdDate;

  private Long lastModifiedBy;

  private LocalDateTime lastModifiedDate;

  public Optional<Long> getCreatedBy() {
    return Optional.ofNullable(this.createdBy);
  }

  public Optional<Long> getLastModifiedBy() {
    return Optional.ofNullable(this.lastModifiedBy);
  }

  public Optional<LocalDateTime> getCreatedDate() {
    return null == this.createdDate ? Optional.empty() : Optional.of(this.createdDate);
  }

  public Optional<LocalDateTime> getLastModifiedDate() {
    return null == this.lastModifiedDate ? Optional.empty() : Optional.of(this.lastModifiedDate);
  }

  public void setId(PK id) {
    super.setId(id);
  }
}
