package com.mpearsall.hr.dto;

import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.repository.primary.UserRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MappingQualifier {
  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;

  public MappingQualifier(UserRepository userRepository, UserDtoMapper userDtoMapper) {
    this.userRepository = userRepository;
    this.userDtoMapper = userDtoMapper;
  }

  @Named("userToUserId")
  long userToUserId(UserDto user) {
    return user.getId();
  }

  @Named("userIdToUser")
  UserDto userIdToUser(Long id) {
    final User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id, User.class));

    return userDtoMapper.toUserDto(user);
  }

  @Named("unwrap")
  <T> T unwrap(Optional<T> optional) {
    return optional.orElse(null);
  }
}
