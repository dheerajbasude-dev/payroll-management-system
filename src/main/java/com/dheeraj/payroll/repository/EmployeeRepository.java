package com.dheeraj.payroll.repository;

import com.dheeraj.payroll.document.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
}
