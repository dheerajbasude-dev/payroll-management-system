package com.dheeraj.payroll.controller;

import com.dheeraj.payroll.document.Employee;
import com.dheeraj.payroll.dto.request.EmployeeRequest;
import com.dheeraj.payroll.mapper.EmployeeMapper;
import com.dheeraj.payroll.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    public EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /*-----------------Create Employee------------------*/
    @Operation(
            summary = "Create a employee to process payroll",
            description = "Employee needs to be created for calculating salary"
    )
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employeeReq) {
        Employee employee = EmployeeMapper.toEntity(employeeReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employee));
    }


    /*-----------------Fetch All Employee-----------------*/
    @Operation(
            summary = "Get all employee information",
            description = "Fetches the all employees in the database"
    )
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }


    /*-----------------Update Employee------------------*/
    @Operation(
            summary = "Update a employee",
            description = "Updating the employee information"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@Valid @PathVariable String id, @RequestBody EmployeeRequest employeeReq) {
        Employee employee = EmployeeMapper.toEntity(employeeReq);
        return ResponseEntity.ok(employeeService.updateEmployee(employee));
    }


    /*-----------------Delete Employee------------------*/
    @Operation(
            summary = "Delete a employee",
            description = "Vanish the employee information"
    )
    @DeleteMapping
    public ResponseEntity<Void> deleteEmployee(@Valid @PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    /*-----------------Get Employee------------------*/
    @Operation(
            summary = "Fetch a employee information",
            description = "Shows a employee information"
    )
    public ResponseEntity<Employee> getEmployeeById(@Valid @PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
}
