package com.mpearsall.hr.entity.primary.client;

import com.mpearsall.hr.entity.primary.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty
  @Column(unique = true)
  private String name;

  @NotEmpty
  private String jdbcUrl;

  @NotEmpty
  private String databaseUser;

  // todo - encrypt
  @NotEmpty
  private String databasePassword;

  @OneToMany(mappedBy = "client")
  private Set<User> users;

  @NotNull
  private LocalDateTime expiry;
}
