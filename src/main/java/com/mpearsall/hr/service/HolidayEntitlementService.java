package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import org.springframework.stereotype.Service;

@Service
public class HolidayEntitlementService {
  public Double calculateHolidayEntitlementInDays(Employee employee, HolidayYear holidayYear) {
    final HolidayEntitlement holidayEntitlement = employee.getHolidayEntitlements().stream()
        .filter(he -> he.getHolidayYear().equals(holidayYear))
        .findFirst().orElse(null);

    double entitlement = 0.0;
    if (holidayEntitlement != null) {
      // todo - make day length variable
      entitlement = holidayEntitlement.getHolidayEntitlementHours() / 7.5;
    }

    return entitlement;
  }
}
