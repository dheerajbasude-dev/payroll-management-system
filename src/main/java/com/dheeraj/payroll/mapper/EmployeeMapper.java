package com.dheeraj.payroll.mapper;

import com.dheeraj.payroll.document.Employee;
import com.dheeraj.payroll.dto.request.EmployeeRequest;

public final class EmployeeMapper {

    private EmployeeMapper() {
        //Prevent instantiation
    }

    public static Employee toEntity(EmployeeRequest employeeReq) {
        Employee employee = new Employee();
        employee.setName(employeeReq.getName());
        employee.setAge(employeeReq.getAge());
        employee.setGender(employeeReq.getGender());
        employee.setDesignation(employeeReq.getDesignation());
        employee.setRating(employeeReq.getRating());
        return employee;
    }
}
