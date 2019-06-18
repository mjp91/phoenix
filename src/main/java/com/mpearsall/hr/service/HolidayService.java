package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.dto.HolidayRequest;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayDate;
import com.mpearsall.hr.entity.holiday.HolidayPeriod;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.repository.HolidayRepository;
import com.mpearsall.hr.repository.HolidayYearRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayService {
  private final HolidayYearRepository holidayYearRepository;
  private final EmployeeService employeeService;
  private final CurrentUserHolidayService currentUserHolidayService;
  private final HolidayRepository holidayRepository;

  public HolidayService(HolidayYearRepository holidayYearRepository, EmployeeService employeeService,
                        CurrentUserHolidayService currentUserHolidayService, HolidayRepository holidayRepository) {
    this.holidayYearRepository = holidayYearRepository;
    this.employeeService = employeeService;
    this.currentUserHolidayService = currentUserHolidayService;
    this.holidayRepository = holidayRepository;
  }

  public static Double calculateHolidayUsed(Employee employee, HolidayYear holidayYear) {
    final List<HolidayDate> holidayDates = employee.getHolidays().stream()
        .filter(holiday -> holiday.getHolidayYear().equals(holidayYear))
        .filter(holiday -> holiday.getApproved() != null && holiday.getApproved())
        .map(Holiday::getHolidayDates)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    double holidayUsed = 0.0;
    for (HolidayDate holidayDate : holidayDates) {
      if (holidayDate.getHolidayPeriod() == HolidayPeriod.ALL_DAY) {
        holidayUsed += 1.0;
      } else {
        holidayUsed += 0.5;
      }
    }

    return holidayUsed;
  }

  @Transactional
  public Holiday requestToHoliday(HolidayRequest holidayRequest) {
    validateHolidayRequest(holidayRequest);
    final Employee currentUserEmployee = employeeService.getCurrentUserEmployee();
    final HolidayYear holidayYear = holidayYearRepository.findById(holidayRequest.getHolidayYearId())
        .orElseThrow(() -> new InvalidDetailsException("Holiday year does not exist"));

    final Holiday holiday = new Holiday();
    holiday.setName(holidayRequest.getName());
    holiday.setHolidayYear(holidayYear);
    holiday.setEmployee(currentUserEmployee);

    final LocalDate startDate = holidayRequest.getStartDate();
    final List<HolidayDate> holidayDates = new ArrayList<>();

    long days = ChronoUnit.DAYS.between(startDate, holidayRequest.getEndDate());
    if (days == 0) {
      days = 1; // if single day
    }

    for (long i = 0; i < days; i++) {
      final HolidayDate holidayDate = new HolidayDate();
      holidayDate.setDate(startDate.plusDays(i));
      holidayDate.setHolidayPeriod(HolidayPeriod.ALL_DAY);
      holidayDates.add(holidayDate);
    }

    holiday.setHolidayDates(holidayDates);

    return holidayRepository.save(holiday);
  }

  private void validateHolidayRequest(HolidayRequest holidayRequest) {
    final LocalDate startDate = holidayRequest.getStartDate();
    final LocalDate endDate = holidayRequest.getEndDate();

    if (startDate.isAfter(endDate)) {
      throw new InvalidDetailsException("Start date must be before end date");
    }

    // todo - factor in days not worked
    final long daysBetween = ChronoUnit.DAYS.between(startDate, holidayRequest.getEndDate());
    final CurrentUserHoliday currentUserHoliday = currentUserHolidayService.getCurrentUserHoliday();

    if (daysBetween > currentUserHoliday.getRemaining()) {
      throw new InvalidDetailsException("Requested holiday would exceed remaining holiday entitlement");
    }
  }
}
