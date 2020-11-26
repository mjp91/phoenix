package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.DateRange;
import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.entity.secondary.absence.Absence;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.entity.secondary.holiday.Holiday;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.repository.primary.UserRepository;
import com.mpearsall.hr.repository.secondary.AbsenceRepository;
import com.mpearsall.hr.repository.secondary.EmployeeRepository;
import com.mpearsall.hr.repository.secondary.HolidayRepository;
import com.mpearsall.hr.util.DateUtil;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
  private final UserRepository userRepository;

  private final EmployeeRepository employeeRepository;

  private final HolidayRepository holidayRepository;

  private final AbsenceRepository absenceRepository;

  public CalendarController(UserRepository userRepository, EmployeeRepository employeeRepository,
                            HolidayRepository holidayRepository, AbsenceRepository absenceRepository) {
    this.userRepository = userRepository;
    this.employeeRepository = employeeRepository;
    this.holidayRepository = holidayRepository;
    this.absenceRepository = absenceRepository;
  }

  @GetMapping(path = "/{username}/{token}", produces = "text/calendar")
  public String getCalendar(@PathVariable String username, @PathVariable String token) {
    final User user = userRepository.findByUsername(username).orElseThrow();

    if (!user.getCalendarToken().equals(token)) {
      throw new PermissionException("Token invalid");
    }

    // get user's employee record
    final Employee employee = employeeRepository.findByUser(user.getId())
        .orElseThrow(() -> new ResourceNotFoundException(user.getId(), Employee.class));

    final Calendar calendar = new Calendar();
    addHolidays(calendar, employee);
    addAbsences(calendar, employee);

    return calendar.toString();
  }

  private void addAbsences(Calendar calendar, Employee employee) {
    final Iterable<Absence> absences = absenceRepository.findAllByEmployee(employee, Pageable.unpaged());

    for (Absence absence : absences) {
      final Date startDate = DateUtil.convertToIcalDateViaInstant(absence.getStart());
      final Date endDate = DateUtil.convertToIcalDateViaInstant(absence.getEnd());

      calendar.getComponents().add(new VEvent(startDate, endDate, "Absence"));
    }
  }

  private void addHolidays(Calendar calendar, Employee employee) {
    final Iterable<Holiday> holidays = holidayRepository.findAllByEmployee(employee, Pageable.unpaged());

    for (Holiday holiday : holidays) {
      final DateRange holidayRange = Holiday.getHolidayRange(holiday);
      final Date startDate = DateUtil.convertToIcalDateViaInstant(holidayRange.getStartDate());
      final Date endDate = DateUtil.convertToIcalDateViaInstant(holidayRange.getEndDate());

      calendar.getComponents().add(new VEvent(startDate, endDate, "Annual Leave: " + holiday.getName()));
    }
  }
}
