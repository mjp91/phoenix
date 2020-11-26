package com.mpearsall.hr.repository.primary;

import com.mpearsall.hr.entity.primary.Config;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface ConfigRepository extends Repository<Config, Long> {
  @Query("SELECT c FROM Config c")
  Config find();

  <S extends Config> S save(S entity);
}
