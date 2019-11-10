package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.holiday.CompanyYear;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import org.springframework.stereotype.Service;

@Service
public class HolidayEntitlementService {
  public static Double calculateHolidayEntitlementInDays(Employee employee, CompanyYear companyYear) {
    final HolidayEntitlement holidayEntitlement = employee.getHolidayEntitlements().stream()
        .filter(he -> he.getCompanyYear().equals(companyYear))
        .findFirst().orElse(null);

    double entitlement = 0.0;
    final double averageDayLength = EmployeeService.getAverageDayLength(employee);
    if (holidayEntitlement != null && averageDayLength > 0.0) {
      entitlement = holidayEntitlement.getHolidayEntitlementHours() / averageDayLength;
    }

    return entitlement;
  }
}
