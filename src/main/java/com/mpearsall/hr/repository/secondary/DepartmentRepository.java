package com.mpearsall.hr.repository.secondary;

import com.mpearsall.hr.entity.secondary.Department;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {
  Iterable<Department> findAllByParentIsNull();
}
