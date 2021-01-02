package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.EmployeeSearchDepartmentNode;
import com.mpearsall.hr.dto.EmployeeSearchEmployeeNode;
import com.mpearsall.hr.dto.EmployeeSearchNode;
import com.mpearsall.hr.entity.primary.user.User;
import com.mpearsall.hr.entity.secondary.Department;
import com.mpearsall.hr.entity.secondary.employee.Employee;
import com.mpearsall.hr.exception.ResourceNotFoundException;
import com.mpearsall.hr.repository.primary.UserRepository;
import com.mpearsall.hr.repository.secondary.DepartmentRepository;
import com.mpearsall.hr.repository.secondary.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;
  private final UserRepository userRepository;

  public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, UserRepository userRepository) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
    this.userRepository = userRepository;
  }

  public List<EmployeeSearchNode> getDepartmentEmployees() {
    final Iterable<Department> rootDepartments = departmentRepository.findAllByParentIsNull();

    return generate(rootDepartments);
  }

  private List<EmployeeSearchNode> generate(Iterable<Department> departments) {
    final List<EmployeeSearchNode> departmentEmployees = new ArrayList<>();

    // recurse departments
    for (Department child : departments) {
      final List<EmployeeSearchNode> children = generate(new ArrayList<>(child.getChildren()));

      // add employees for this department
      for (Employee employee : employeeRepository.findByDepartment(child)) {
        final User user = userRepository.findById(employee.getUser())
            .orElseThrow(() -> new ResourceNotFoundException(employee.getUser(), User.class));
        children.add(new EmployeeSearchEmployeeNode(employee.getId(), user.getFullName()));
      }

      departmentEmployees.add(new EmployeeSearchDepartmentNode(child.getId(), child.getTitle(), children));
    }

    return departmentEmployees;
  }
}
