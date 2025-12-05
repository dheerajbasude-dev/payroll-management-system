package com.dheeraj.payroll.service;

import com.dheeraj.payroll.document.Payroll;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface PayrollService {

    Payroll createPayroll(String employeeId, LocalDate payDate);

    List<Payroll> getAllPayroll();

    List<Payroll> getEmployeePayrollsByYear(String employeeId, int year);

    List<Payroll> getEmployeePayrollsByYearAndMonth(String employeeId, YearMonth parse);

    List<Payroll> getEmployeePayrollsByDatesRange(@Valid String employeeId, YearMonth parse, YearMonth parse1);

    void fetchEmployeeData(List<Payroll> payrolls);
}
