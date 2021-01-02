package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.primary.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UpdateUser implements Serializable {
  private Long id;

  private Collection<Role> roles;

  private Boolean totpEnabled;
}
