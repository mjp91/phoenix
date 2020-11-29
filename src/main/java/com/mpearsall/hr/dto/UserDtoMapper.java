package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.primary.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MappingQualifier.class)
public interface UserDtoMapper {
  UserDto toUserDto(User user);
}
