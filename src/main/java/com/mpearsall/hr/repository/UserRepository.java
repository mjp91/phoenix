package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  User findByUsername(String username);
}
