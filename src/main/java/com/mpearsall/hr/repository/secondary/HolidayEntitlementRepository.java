package com.mpearsall.hr.repository.secondary;

import com.mpearsall.hr.entity.secondary.holiday.HolidayEntitlement;
import org.springframework.data.repository.CrudRepository;

public interface HolidayEntitlementRepository extends CrudRepository<HolidayEntitlement, Long> {
  Iterable<HolidayEntitlement> findAllByEmployee_Id(Long id);
}
