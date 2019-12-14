package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findByPasswordResetToken(String passwordResetToken);
}
