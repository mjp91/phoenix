package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.Config;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface ConfigRepository extends Repository<Config, Long> {
  @Query("SELECT c FROM Config c")
  Config find();

  <S extends Config> S save(S entity);
}
