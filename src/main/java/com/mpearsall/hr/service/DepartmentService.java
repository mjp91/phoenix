package com.mpearsall.hr.service;

import com.mpearsall.hr.dto.EmployeeSearchDepartmentNode;
import com.mpearsall.hr.dto.EmployeeSearchEmployeeNode;
import com.mpearsall.hr.dto.EmployeeSearchNode;
import com.mpearsall.hr.entity.Department;
import com.mpearsall.hr.entity.employee.Employee;
import com.mpearsall.hr.repository.DepartmentRepository;
import com.mpearsall.hr.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;

  public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
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
        children.add(new EmployeeSearchEmployeeNode(employee.getId(), employee.getUser().getFullName()));
      }

      departmentEmployees.add(new EmployeeSearchDepartmentNode(child.getId(), child.getTitle(), children));
    }

    return departmentEmployees;
  }
}
