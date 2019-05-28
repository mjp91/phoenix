package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.HolidayEntitlement;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.factory.HolidayYearFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class HolidayEntitlementServiceTest extends HrApplicationTests {

  @Autowired
  private HolidayEntitlementService holidayEntitlementService;

  @Test
  public void calculateHolidayEntitlementInDays() {
    final HolidayYear holidayYear = HolidayYearFactory.generateForCurrentYear();

    final double dayLength = 7.5; // todo - make day length variable
    final double entitlement = 25.0;

    final HolidayEntitlement holidayEntitlement = new HolidayEntitlement();
    holidayEntitlement.setHolidayYear(holidayYear);
    holidayEntitlement.setHolidayEntitlementHours(dayLength * entitlement);

    final Employee employee = new Employee();
    employee.setHolidayEntitlements(Collections.singletonList(holidayEntitlement));

    final Double entitlementInDays = holidayEntitlementService.calculateHolidayEntitlementInDays(employee, holidayYear);
    Assert.assertEquals(Double.valueOf(entitlement), entitlementInDays);
  }
}