package com.mpearsall.hr.repository.secondary;

import com.mpearsall.hr.entity.secondary.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface CompanyRepository extends Repository<Company, Long> {
  @Query("SELECT c FROM Company c")
  Company find();

  <S extends Company> S save(S entity);
}
