package com.mpearsall.hr.repository.primary;

import com.mpearsall.hr.entity.primary.client.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
