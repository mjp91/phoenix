package com.mpearsall.hr.controller;

import com.mpearsall.hr.dto.DateRange;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.holiday.Holiday;
import com.mpearsall.hr.entity.user.User;
import com.mpearsall.hr.exception.PermissionException;
import com.mpearsall.hr.repository.EmployeeRepository;
import com.mpearsall.hr.repository.HolidayRepository;
import com.mpearsall.hr.repository.UserRepository;
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

  public CalendarController(UserRepository userRepository, EmployeeRepository employeeRepository, HolidayRepository holidayRepository) {
    this.userRepository = userRepository;
    this.employeeRepository = employeeRepository;
    this.holidayRepository = holidayRepository;
  }

  @GetMapping(path = "/{username}/{token}", produces = "text/calendar")
  public String getCalendar(@PathVariable String username, @PathVariable String token) {
    final User user = userRepository.findByUsername(username).orElseThrow();

    if (!user.getCalendarToken().equals(token)) {
      throw new PermissionException("Token invalid");
    }

    // get user's employee record
    final Employee employee = employeeRepository.findByUser(user);

    final Calendar calendar = new Calendar();

    final Iterable<Holiday> holidays = holidayRepository.findAllByEmployee(employee, Pageable.unpaged());

    for (Holiday holiday : holidays) {
      final DateRange holidayRange = Holiday.getHolidayRange(holiday);
      final Date startDate = DateUtil.convertToIcalDateViaInstant(holidayRange.getStartDate());
      final Date endDate = DateUtil.convertToIcalDateViaInstant(holidayRange.getEndDate());

      calendar.getComponents().add(new VEvent(startDate, endDate, holiday.getName()));
    }

    return calendar.toString();
  }
}
