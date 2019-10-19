package com.mpearsall.hr.service;

import com.mpearsall.hr.HrApplicationTests;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.entity.user.User;
import com.mpearsall.hr.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

public class EmployeeServiceTest extends HrApplicationTests {
  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private UserRepository userRepository;

  @Test
  @WithMockUser(username = "buzz", roles = {"ADMIN"})
  public void createEmployee() {
    User user = new User("test", "test@example.com", "Test User");
    user = userRepository.save(user);

    final Employee employee = employeeService.createEmployee(user);
    Assert.assertNotNull(employee);
    Assert.assertNotNull(employee.getEmployeeWeek());
    Assert.assertNotNull(employee.getHolidayEntitlements());
  }
}
