package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.CurrentUserHoliday;
import com.mpearsall.hr.dto.Email;
import com.mpearsall.hr.dto.EmailTemplate;
import com.mpearsall.hr.dto.HolidayRequest;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.holiday.HolidayDate;
import com.mpearsall.hr.entity.holiday.HolidayPeriod;
import com.mpearsall.hr.entity.holiday.HolidayYear;
import com.mpearsall.hr.entity.user.User;
import com.mpearsall.hr.exception.InvalidDetailsException;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.repository.HolidayRepository;
import com.mpearsall.hr.repository.HolidayYearRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HolidayService {
  private static final String APPROVED_TEMPLATE = "approved";

  private final HolidayYearRepository holidayYearRepository;
  private final EmployeeService employeeService;
  private final CurrentUserHolidayService currentUserHolidayService;
  private final HolidayRepository holidayRepository;
  private final EmailService emailService;

  public HolidayService(HolidayYearRepository holidayYearRepository, EmployeeService employeeService,
                        CurrentUserHolidayService currentUserHolidayService, HolidayRepository holidayRepository,
                        EmailService emailService) {
    this.holidayYearRepository = holidayYearRepository;
    this.employeeService = employeeService;
    this.currentUserHolidayService = currentUserHolidayService;
    this.holidayRepository = holidayRepository;
    this.emailService = emailService;
  }

  static Double calculateHolidayUsed(Employee employee, HolidayYear holidayYear) {
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

    final Set<LocalDate> dates = EmployeeService.calculateDaysWorkedBetween(currentUserEmployee, holidayRequest.getStartDate(), holidayRequest.getEndDate());

    // only allow half days to be requested for single dates
    if (holidayRequest.getHolidayPeriod() != HolidayPeriod.ALL_DAY && dates.size() > 1) {
      throw new InvalidDetailsException("Partial dates must be requested separately");
    }

    // create HolidayDate for each day
    final List<HolidayDate> holidayDates = new ArrayList<>();
    for (LocalDate date : dates) {
      final HolidayDate holidayDate = new HolidayDate();
      holidayDate.setDate(date);
      holidayDate.setHolidayPeriod(holidayRequest.getHolidayPeriod());
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

  @Transactional
  public Holiday approveHoliday(Long id) {
    final Holiday holiday = modifyHolidayApproval(id, true, null);

    sendApprovalEmail(holiday);

    return holiday;
  }

  private void sendApprovalEmail(Holiday holiday) {
    final User user = holiday.getEmployee().getUser();

    final String to = user.getEmail();
    final Map<String, Object> args = Map.of("fullName", user.getFullName());

    final EmailTemplate emailTemplate = new EmailTemplate(APPROVED_TEMPLATE, args);
    emailService.sendHtml(new Email(Collections.singletonList(to), "Holiday Approved"), emailTemplate);
  }

  @Transactional
  public Holiday disapproveHoliday(Long id, String reason) {
    return modifyHolidayApproval(id, false, reason);
  }

  private Holiday modifyHolidayApproval(Long id, boolean approved, @Nullable String reason) {
    final Holiday holiday = holidayRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id, Holiday.class));

    // check is manager
    final Employee currentUserEmployee = employeeService.getCurrentUserEmployee();
    final Employee holidayEmployee = holiday.getEmployee();
    final Employee manager = holidayEmployee.getManager();

    if (manager == null || !manager.equals(currentUserEmployee)) {
      throw new PermissionException("User is not the holiday creator's manager");
    }

    holiday.setApproved(approved);
    holiday.setDisapprovalReason(reason);

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
    final long daysBetween = EmployeeService.calculateDaysWorkedBetween(employee, startDate, endDate).size();

    if (daysBetween == 0) {
      throw new InvalidDetailsException("Requested holiday dates are not worked");
    }

    final CurrentUserHoliday currentUserHoliday = currentUserHolidayService.getCurrentUserHoliday();

    if (daysBetween > currentUserHoliday.getRemaining()) {
      throw new InvalidDetailsException("Requested holiday would exceed remaining holiday entitlement");
    }

    // check not overlapping existing holidays
    final Collection<Holiday> existingHolidays = holidayRepository.findAllInRange(startDate, endDate, employee);
    if (!existingHolidays.isEmpty()) {
      throw new InvalidDetailsException("Requested holiday overlaps existing holidays");
    }
  }

}
