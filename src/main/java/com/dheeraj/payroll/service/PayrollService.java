package com.dheeraj.payroll.service;

import com.dheeraj.payroll.document.Payroll;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface PayrollService {

    Payroll createPayroll(String employeeId, LocalDate payDate);

    List<Payroll> getAllPayroll();

    List<Payroll> getEmployeePayrollsByEmployeeId(String employeeId);

    List<Payroll> getEmployeePayrollsByYear(String employeeId, int year);

    List<Payroll> getEmployeePayrollsByYearAndMonth(String employeeId, YearMonth yearMonth);

    List<Payroll> getEmployeePayrollsByDatesRange(String employeeId, YearMonth startYM, YearMonth endYM);

    void fetchEmployeeData(List<Payroll> payrolls);

    List<String> extractYearMonth(YearMonth startYM, YearMonth endYM);

}
