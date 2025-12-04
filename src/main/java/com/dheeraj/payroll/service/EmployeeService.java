package com.dheeraj.payroll.service;

import com.dheeraj.payroll.document.Employee;
import jakarta.validation.Valid;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(String id);

    Employee getEmployeeById(String id);
}
