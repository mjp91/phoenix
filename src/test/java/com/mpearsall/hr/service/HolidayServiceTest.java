package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.dto.HolidayRequest;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.CompanyYear;
import com.mpearsall.hr.entity.secondary.holiday.Holiday;
import com.mpearsall.hr.entity.secondary.holiday.HolidayDate;
import com.mpearsall.hr.entity.secondary.holiday.HolidayEntitlement;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.factory.CompanyYearFactory;
import com.mpearsall.hr.repository.secondary.EmployeeRepository;
import com.mpearsall.hr.repository.secondary.HolidayRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class HolidayServiceTest extends HrApplicationTests {
  @Autowired
  private HolidayService holidayService;

  @Autowired
  private CompanyYearService companyYearService;

  @Autowired
  private HolidayRepository holidayRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private CompanyYearFactory companyYearFactory;

  @Test
  public void calculateHolidayUsed() {
    final CompanyYear companyYear = companyYearFactory.generateForCurrentYear();

    final HolidayEntitlement holidayEntitlement = new HolidayEntitlement();
    holidayEntitlement.setCompanyYear(companyYear);
    holidayEntitlement.setHolidayEntitlementHours(7.5 * 25);

    final LocalDate now = LocalDate.now();
    final HolidayDate holidayDateToday = new HolidayDate();
    holidayDateToday.setDate(now);

    final HolidayDate holidayDateTomorrow = new HolidayDate();
    holidayDateTomorrow.setDate(now.plusDays(1));

    final Holiday holiday = new Holiday();
    holiday.setCompanyYear(companyYear);
    holiday.setHolidayDates(Arrays.asList(holidayDateToday, holidayDateTomorrow));
    holiday.setApproved(true);

    final Employee employee = new Employee();
    employee.setHolidayEntitlements(Collections.singletonList(holidayEntitlement));
    employee.setHolidays(Collections.singletonList(holiday));

    final Double holidayUsed = HolidayService.calculateHolidayUsed(employee, companyYear);
    Assertions.assertEquals(Double.valueOf(2.0), holidayUsed);
  }

  @Test
  @WithMockUser(username = "matt")
  public void requestToHolidayBackwardsDates() {
    final HolidayRequest holidayRequest = new HolidayRequest();
    holidayRequest.setStartDate(LocalDate.now());
    holidayRequest.setEndDate(LocalDate.now().minusDays(1L));
    holidayRequest.setCompanyYearId(companyYear.getId());

    Assertions.assertThrows(InvalidDetailsException.class, () -> holidayService.requestToHoliday(holidayRequest));
  }

  @Test
  @WithMockUser(username = "matt")
  public void requestToHolidayPastDates() {
    final HolidayRequest holidayRequest = new HolidayRequest();
    holidayRequest.setStartDate(LocalDate.now().minusDays(2L));
    holidayRequest.setEndDate(LocalDate.now().plusDays(4L));
    holidayRequest.setCompanyYearId(companyYear.getId());

    Assertions.assertThrows(InvalidDetailsException.class, () -> holidayService.requestToHoliday(holidayRequest));
  }

  @Test
  @WithMockUser(username = "matt")
  public void testApproveFromInvalidUser() {
    final Employee buzz = employeeRepository.findByUser(1L).orElseThrow();
    final Holiday holiday = holidayRepository.findAllByEmployee(buzz, Pageable.unpaged()).stream()
        .findFirst().orElseThrow();

    Assertions.assertThrows(PermissionException.class, () -> holidayService.approveHoliday(holiday.getId()));
  }
}
