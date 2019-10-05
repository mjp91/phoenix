package com.mpearsall.hr.repository;

import com.mpearsall.hr.entity.employee.JobRole;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobRoleRepository extends PagingAndSortingRepository<JobRole, Long> {
}
