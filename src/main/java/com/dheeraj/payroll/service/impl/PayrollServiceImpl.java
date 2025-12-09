package com.dheeraj.payroll.service.impl;

import com.dheeraj.payroll.document.Employee;
import com.dheeraj.payroll.document.Payroll;
import com.dheeraj.payroll.exception.InvalidDateException;
import com.dheeraj.payroll.exception.ResourceNotFoundException;
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
        List<Payroll> payrolls = payrollRepository.findByEmployeeIdAndPayYear(employeeId, year);
        if (payrolls.isEmpty()) {
            throw new ResourceNotFoundException("No payrolls found for employee "+ employeeId + " for year "+year);
        }
        fetchEmployeeData(payrolls);
        return payrolls;
    }

    @Override
    public List<Payroll> getEmployeePayrollsByYearAndMonth(String employeeId, YearMonth yearMonth) {
        String month = yearMonth.getMonth().name();
        int year = yearMonth.getYear();
        List<Payroll> payrolls = payrollRepository.findByEmployeeIdAndPayMonthAndPayYear(employeeId, month, year);
        if (payrolls.isEmpty()) {
            throw new ResourceNotFoundException("No payrolls found for employee "+ employeeId + " for month "+month+" and year "+year);
        }
        fetchEmployeeData(payrolls);
        return payrolls;
    }


    @Override
    public List<Payroll> getEmployeePayrollsByDatesRange(String employeeId, YearMonth startYM, YearMonth endYM) {
        List<String> yearMonths = extractYearMonth(startYM, endYM);
        List<Payroll> payrolls = payrollRepository.findByEmployeeIdAndPayDateBetween(employeeId, yearMonths.get(0), yearMonths.get(1));

        if (payrolls.isEmpty()) {
            throw new ResourceNotFoundException("No payrolls found for employee " + employeeId + " between " + startYM + " and " + endYM);
        }
        fetchEmployeeData(payrolls);
        return payrolls;
    }

    @Override
    public void fetchEmployeeData(List<Payroll> payrolls) {
        for (Payroll payroll : payrolls) {
            Employee employee = employeeService.getEmployeeById(payroll.getEmployeeId());
            payroll.setEmployee(employee);
        }
    }

    @Override
    public List<String> extractYearMonth(YearMonth startYM, YearMonth endYM) {
        if (startYM.isAfter(endYM)) {
            throw new InvalidDateException("Start year month cannot be after end year month.");
        }
        String startDate =  startYM.atDay(1).toString();
        String endDate = endYM.atEndOfMonth().toString();

        return List.of(startDate, endDate);
    }

}
