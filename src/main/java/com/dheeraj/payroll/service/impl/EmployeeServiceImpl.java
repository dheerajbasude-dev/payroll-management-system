package com.dheeraj.payroll.service.impl;

import com.dheeraj.payroll.document.Employee;
import com.dheeraj.payroll.exception.EmployeeNotFoundException;
import com.dheeraj.payroll.repository.EmployeeRepository;
import com.dheeraj.payroll.service.EmployeeService;
import com.dheeraj.payroll.utils.PayrollCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        employee.setSalary(PayrollCalculator.computeSalary(employee));
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(String id, Employee employee) {
        Employee updatedEmployee = getEmployeeById(id);
        updatedEmployee.setName(employee.getName());
        updatedEmployee.setAge(employee.getAge());
        updatedEmployee.setGender(employee.getGender());
        updatedEmployee.setDesignation(employee.getDesignation());
        updatedEmployee.setRating(employee.getRating());
        updatedEmployee.setSalary(PayrollCalculator.computeSalary(employee));
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(String id) {
        Employee existsEmployee = getEmployeeById(id);
        employeeRepository.delete(existsEmployee);
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not found id : "+id)
        );
    }
}
