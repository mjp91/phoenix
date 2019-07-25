package com.mpearsall.hr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Role implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String name;

  @ManyToMany(mappedBy = "roles")
  private Collection<User> users;

  @ManyToMany
  private Collection<Privilege> privileges;
}
