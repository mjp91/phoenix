package com.mpearsall.hr.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserDto extends EntityDto {
  private String fullName;
  private String username;
  private String email;
  private String calendarToken;
  private Collection<RoleDto> roles;
  private boolean totpEnabled;
}
