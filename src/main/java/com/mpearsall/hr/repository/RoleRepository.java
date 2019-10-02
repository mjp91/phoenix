package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.user.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
