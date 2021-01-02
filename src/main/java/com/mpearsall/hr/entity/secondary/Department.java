package com.mpearsall.hr.entity.secondary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Department extends CustomAbstractAuditable<Long> {
  @NotEmpty
  private String title;

  @ManyToOne
  @JsonIgnore
  @ToString.Exclude
  private Department parent;

  @OneToMany(mappedBy = "parent")
  private Collection<Department> children;

  @JsonIgnore
  public Department getParent() {
    return parent;
  }

  @JsonProperty
  public void setParent(Department parent) {
    this.parent = parent;
  }

  public void setChildren(Collection<Department> children) {
    for (Department department : children) {
      department.setParent(this);
    }

    this.children = children;
  }
}
