package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.Department;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {
}
