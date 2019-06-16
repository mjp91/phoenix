package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import org.springframework.stereotype.Service;

@Service
public class HolidayEntitlementService {
  public static Double calculateHolidayEntitlementInDays(Employee employee, HolidayYear holidayYear) {
    final HolidayEntitlement holidayEntitlement = employee.getHolidayEntitlements().stream()
        .filter(he -> he.getHolidayYear().equals(holidayYear))
        .findFirst().orElse(null);

    double entitlement = 0.0;
    if (holidayEntitlement != null) {
      entitlement = holidayEntitlement.getHolidayEntitlementHours() / EmployeeService.getAverageDayLength(employee);
    }

    return entitlement;
  }
}
