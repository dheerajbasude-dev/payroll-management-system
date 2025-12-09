package com.dheeraj.payroll.repository;

import com.dheeraj.payroll.document.Payroll;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PayrollRepository extends MongoRepository<Payroll,String> {

    List<Payroll> findByEmployeeId(String employeeId);

    List<Payroll> findByEmployeeIdAndPayYear(String employeeId, int year);

    List<Payroll> findByEmployeeIdAndPayMonthAndPayYear(String employeeId, String month, int year);

    List<Payroll> findByEmployeeIdAndPayDateBetween(String employeeId, String startDate, String endDate);

    List<Payroll> findByPayYear(int year);

    List<Payroll> findByPayMonthAndPayYear(String month, int year);

    List<Payroll> findByPayDateBetween(String startDate, String endDate);

}
