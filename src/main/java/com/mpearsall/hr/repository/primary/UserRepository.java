package com.mpearsall.hr.repository.primary;

import com.mpearsall.hr.entity.primary.client.Client;
import com.mpearsall.hr.entity.primary.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findByUsernameAndClient(String username, Client client);

  Optional<User> findByPasswordResetToken(String passwordResetToken);

  boolean existsByUsername(String username);

  Iterable<User> findAllByClient(Client client);

  Optional<User> findByIdAndClient(Long id, Client client);
}
