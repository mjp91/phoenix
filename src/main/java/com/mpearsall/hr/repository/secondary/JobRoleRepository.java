package com.mpearsall.hr.repository.secondary;

import com.mpearsall.hr.entity.secondary.employee.JobRole;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobRoleRepository extends PagingAndSortingRepository<JobRole, Long> {
}
