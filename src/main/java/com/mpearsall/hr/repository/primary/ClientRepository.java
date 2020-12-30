package com.mpearsall.hr.repository.primary;

import com.mpearsall.hr.entity.primary.client.Client;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotEmpty;

public interface ClientRepository extends CrudRepository<Client, Long> {
  boolean existsByName(@NotEmpty String name);
}
