package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.dto.HolidayRequest;
import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayDate;
import com.mpearsall.hr.entity.holiday.HolidayPeriod;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.repository.HolidayRepository;
import com.mpearsall.hr.repository.HolidayYearRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        .filter(holiday -> !holiday.isCancelled())
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
    final Employee currentUserEmployee = employeeService.getCurrentUserEmployee();
    final HolidayYear holidayYear = holidayYearRepository.findById(holidayRequest.getHolidayYearId())
        .orElseThrow(() -> new InvalidDetailsException("Holiday year does not exist"));

    validateHolidayRequest(holidayRequest, currentUserEmployee, holidayYear);

    final Holiday holiday = new Holiday();
    holiday.setName(holidayRequest.getName());
    holiday.setHolidayYear(holidayYear);
    holiday.setEmployee(currentUserEmployee);

    // no manager approved by default
    if (currentUserEmployee.getManager() == null) {
      holiday.setApproved(true);
    }

    final LocalDate startDate = holidayRequest.getStartDate();
    final List<HolidayDate> holidayDates = new ArrayList<>();

    final Set<LocalDate> dates = calculateHolidayDates(currentUserEmployee, startDate, holidayRequest.getEndDate());

    for (LocalDate date : dates) {
      final HolidayDate holidayDate = new HolidayDate();
      holidayDate.setDate(date);
      holidayDate.setHolidayPeriod(HolidayPeriod.ALL_DAY);
      holidayDates.add(holidayDate);
    }

    holiday.setHolidayDates(holidayDates);

    return holidayRepository.save(holiday);
  }

  @Transactional
  public Holiday cancelHoliday(Long holidayId) {
    final Holiday holiday = holidayRepository.findById(holidayId)
        .orElseThrow(() -> new ResourceNotFoundException(holidayId, Holiday.class));

    final Employee currentUserEmployee = employeeService.getCurrentUserEmployee();

    if (holiday.getEmployee() != currentUserEmployee) {
      throw new PermissionException("User does not have permission to cancel other users holiday");
    }

    holiday.setCancelled(true);

    return holiday;
  }

  private void validateHolidayRequest(HolidayRequest holidayRequest, Employee employee, HolidayYear holidayYear) {
    final LocalDate startDate = holidayRequest.getStartDate();
    final LocalDate endDate = holidayRequest.getEndDate();

    // check not backwards
    if (startDate.isAfter(endDate)) {
      throw new InvalidDetailsException("Start date must be before end date");
    }

    // check not in the past
    final LocalDate today = LocalDate.now();
    if (startDate.isBefore(today)) {
      throw new InvalidDetailsException("Requested days are in the past");
    }

    // check in requested year
    if (startDate.isBefore(holidayYear.getYearStart()) || endDate.isAfter(holidayYear.getYearEnd())) {
      throw new InvalidDetailsException("Requested dates fall outside of requested year");
    }
    final long daysBetween = calculateHolidayDates(employee, startDate, endDate).size();

    if (daysBetween == 0) {
      throw new InvalidDetailsException("Requested holiday dates are not worked");
    }

    final CurrentUserHoliday currentUserHoliday = currentUserHolidayService.getCurrentUserHoliday();

    if (daysBetween > currentUserHoliday.getRemaining()) {
      throw new InvalidDetailsException("Requested holiday would exceed remaining holiday entitlement");
    }

    // check not overlapping existing holidays
    final Collection<Holiday> existingHolidays = holidayRepository.findAllInRange(startDate, endDate, employee);
    if (existingHolidays.size() > 0) {
      throw new InvalidDetailsException("Requested holiday overlaps existing holidays");
    }
  }

  private Set<LocalDate> calculateHolidayDates(Employee employee, LocalDate startDate, LocalDate endDate) {
    // check entitlement
    final Set<DayOfWeek> daysWorked = Employee.getDaysWorked(employee);

    return Stream.iterate(startDate, d -> d.plusDays(1))
        .limit(startDate.until(endDate.plusDays(1), ChronoUnit.DAYS))
        .filter(d -> daysWorked.contains(d.getDayOfWeek()))
        .collect(Collectors.toSet());
  }

}
