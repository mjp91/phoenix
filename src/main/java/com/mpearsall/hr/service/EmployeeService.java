package com.mpearsall.hr.service;

import com.mpearsall.hr.entity.Employee;
import com.mpearsall.hr.entity.EmployeeDay;
import com.mpearsall.hr.entity.EmployeeWeek;
import com.mpearsall.hr.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final UserService userService;

  public EmployeeService(EmployeeRepository employeeRepository, UserService userService) {
    this.employeeRepository = employeeRepository;
    this.userService = userService;
  }

  public Employee getCurrentUserEmployee() {
    final User currentUser = userService.getCurrentUser();

    Employee employee = null;
    if (currentUser != null) {
      employee = employeeRepository.findByUser_Username(currentUser.getUsername());
    }

    return employee;
  }

  public static double getAverageDayLength(Employee employee) {
    final EmployeeWeek employeeWeek = employee.getEmployeeWeek();

    int daysWorked = 0;
    double totalHours = 0.0;

    if (employee.getEmployeeWeek() != null) {
      final EmployeeDay[] employeeDays = new EmployeeDay[]{
          employeeWeek.getMonday(),
          employeeWeek.getTuesday(),
          employeeWeek.getWednesday(),
          employeeWeek.getThursday(),
          employeeWeek.getFriday(),
          employeeWeek.getSaturday(),
          employeeWeek.getSunday()
      };

      for (EmployeeDay employeeDay : employeeDays) {
        if (employeeDay != null) {
          daysWorked++;

          final long minutesBetween = ChronoUnit.MINUTES.between(employeeDay.getStart(), employeeDay.getEnd());
          totalHours += minutesBetween / 60.0;
        }
      }
    }

    if (daysWorked == 0.0) {
      return 0.0;
    }

    return totalHours / daysWorked;
  }
}
