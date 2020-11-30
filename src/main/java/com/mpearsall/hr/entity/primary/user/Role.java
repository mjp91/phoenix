package com.mpearsall.hr.entity.primary.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
public class Role implements Serializable {
  public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
  public static final String ADMIN = "ROLE_ADMIN";
  public static final String USER = "ROLE_USER";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  @ToString.Exclude
  private Collection<User> users;

  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<Privilege> privileges;
}
