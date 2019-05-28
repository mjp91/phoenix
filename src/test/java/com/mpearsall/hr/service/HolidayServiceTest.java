package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayDate;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.factory.HolidayYearFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class HolidayServiceTest extends HrApplicationTests {
  @Autowired
  private HolidayService holidayService;

  @Test
  public void calculateHolidayUsed() {
    final HolidayYear holidayYear = HolidayYearFactory.generateForCurrentYear();

    final HolidayEntitlement holidayEntitlement = new HolidayEntitlement();
    holidayEntitlement.setHolidayYear(holidayYear);
    holidayEntitlement.setHolidayEntitlementHours(7.5 * 25);

    final LocalDate now = LocalDate.now();
    final HolidayDate holidayDateToday = new HolidayDate();
    holidayDateToday.setDate(now);

    final HolidayDate holidayDateTomorrow = new HolidayDate();
    holidayDateTomorrow.setDate(now.plusDays(1));

    final Holiday holiday = new Holiday();
    holiday.setHolidayYear(holidayYear);
    holiday.setHolidayDates(Arrays.asList(holidayDateToday, holidayDateTomorrow));
    holiday.setApproved(true);

    final Employee employee = new Employee();
    employee.setHolidayEntitlements(Collections.singletonList(holidayEntitlement));
    employee.setHolidays(Collections.singletonList(holiday));

    final Double holidayUsed = holidayService.calculateHolidayUsed(employee, holidayYear);
    Assert.assertEquals(Double.valueOf(2.0), holidayUsed);
  }
}