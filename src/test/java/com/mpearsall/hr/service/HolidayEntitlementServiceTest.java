package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.EmployeeDay;
import com.mpearsall.hr.entity.EmployeeWeek;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.factory.HolidayYearFactory;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Collections;

public class HolidayEntitlementServiceTest extends HrApplicationTests {

  @Test
  public void calculateHolidayEntitlementInDays() {
    final HolidayYear holidayYear = HolidayYearFactory.generateForCurrentYear();

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
    holidayEntitlement.setHolidayYear(holidayYear);
    holidayEntitlement.setHolidayEntitlementHours(EmployeeService.getAverageDayLength(employee) * entitlement);

    employee.setHolidayEntitlements(Collections.singletonList(holidayEntitlement));

    final Double entitlementInDays = HolidayEntitlementService.calculateHolidayEntitlementInDays(employee, holidayYear);
    Assert.assertEquals(Double.valueOf(entitlement), entitlementInDays);
  }
}