package com.mpearsall.hr.repository.primary;

import com.mpearsall.hr.entity.primary.user.Role;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
  Optional<Role> findRoleByName(@NotEmpty String name);
}
