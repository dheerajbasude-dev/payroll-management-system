package com.dheeraj.payroll.service.impl;

import com.dheeraj.payroll.document.Employee;
import com.dheeraj.payroll.document.Payroll;
import com.dheeraj.payroll.exception.EmployeeNotFoundException;
import com.dheeraj.payroll.repository.PayrollRepository;
import com.dheeraj.payroll.service.EmployeeService;
import com.dheeraj.payroll.service.PayrollService;
import com.dheeraj.payroll.utils.PayrollCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@Transactional
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeService employeeService;

    public PayrollServiceImpl(PayrollRepository payrollRepository, EmployeeService employeeService) {
        this.payrollRepository = payrollRepository;
        this.employeeService = employeeService;
    }



    @Override
    public Payroll createPayroll(String employeeId, LocalDate payDate) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        double grossSalary = employee.getSalary();
        double taxAmount = PayrollCalculator.computeTax(grossSalary);
        double netSalary = PayrollCalculator.roundToTwoDecimalPlaces(grossSalary - taxAmount);
        YearMonth yearMonth = YearMonth.from(payDate);
        Payroll payroll = new Payroll(
                employeeId,
                payDate.toString(),
                grossSalary,
                taxAmount,
                netSalary,
                yearMonth.getMonth().name(),
                yearMonth.getYear()

        );
        Payroll savedPayroll = payrollRepository.save(payroll);
        savedPayroll.setEmployee(employee);
        return savedPayroll;
    }

    @Override
    public List<Payroll> getAllPayroll() {
        List<Payroll> payrolls = payrollRepository.findAll();
        fetchEmployeeData(payrolls);
        return payrolls;
    }

    @Override
    public List<Payroll> getEmployeePayrollsByYear(String employeeId, int year) {
        return List.of();
    }

    @Override
    public List<Payroll> getEmployeePayrollsByYearAndMonth(String employeeId, YearMonth parse) {
        return List.of();
    }


    @Override
    public List<Payroll> getEmployeePayrollsByDatesRange(String employeeId, YearMonth parse, YearMonth parse1) {
        return List.of();
    }

    @Override
    public void fetchEmployeeData(List<Payroll> payrolls) {
        for (Payroll payroll : payrolls) {
            Employee employee = employeeService.getEmployeeById(payroll.getEmployeeId());
            payroll.setEmployee(employee);
        }
    }
}
