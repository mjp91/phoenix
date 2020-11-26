package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.employee.EmployeeDay;
import com.mpearsall.hr.entity.secondary.employee.EmployeeWeek;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.entity.secondary.holiday.HolidayEntitlement;
import com.mpearsall.hr.factory.CompanyYearFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.Collections;

public class HolidayEntitlementServiceTest extends HrApplicationTests {

  @Autowired
  private CompanyYearFactory companyYearFactory;

  @Test
  public void calculateHolidayEntitlementInDays() {
    final CompanyYear companyYear = companyYearFactory.generateForCurrentYear();

    final double entitlement = 25.0;
    final LocalTime start = LocalTime.of(9, 0);
    final LocalTime end = LocalTime.of(17, 0);

    final Employee employee = new Employee();

    final EmployeeWeek employeeWeek = new EmployeeWeek();
    employeeWeek.setMonday(new EmployeeDay(start, end));
    employeeWeek.setTuesday(new EmployeeDay(start, end));
    employeeWeek.setWednesday(new EmployeeDay(start, end));
    employeeWeek.setThursday(new EmployeeDay(start, end));
    employeeWeek.setFriday(new EmployeeDay(start, end));

    employee.setEmployeeWeek(employeeWeek);

    final HolidayEntitlement holidayEntitlement = new HolidayEntitlement();
    holidayEntitlement.setCompanyYear(companyYear);
    holidayEntitlement.setHolidayEntitlementHours(EmployeeService.getAverageDayLength(employee) * entitlement);

    employee.setHolidayEntitlements(Collections.singletonList(holidayEntitlement));

    final Double entitlementInDays = HolidayEntitlementService.calculateHolidayEntitlementInDays(employee, companyYear);
    Assertions.assertEquals(Double.valueOf(entitlement), entitlementInDays);
  }
}
