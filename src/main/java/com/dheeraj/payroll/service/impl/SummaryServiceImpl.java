package com.dheeraj.payroll.service.impl;

import com.dheeraj.payroll.document.Payroll;
import com.dheeraj.payroll.dto.response.SummaryResponse;
import com.dheeraj.payroll.exception.ResourceNotFoundException;
import com.dheeraj.payroll.repository.PayrollRepository;
import com.dheeraj.payroll.service.PayrollService;
import com.dheeraj.payroll.service.SummaryService;
import com.dheeraj.payroll.utils.PayrollCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

@Service
@Transactional
public class SummaryServiceImpl implements SummaryService {

    public final PayrollRepository payrollRepository;
    public final PayrollService payrollService;

    public SummaryServiceImpl(PayrollRepository payrollRepository, PayrollService payrollService) {
        this.payrollRepository = payrollRepository;
        this.payrollService = payrollService;
    }

    @Override
    public SummaryResponse getAllSummary() {
        List<Payroll> payrolls = payrollRepository.findAll();
        payrollService.fetchEmployeeData(payrolls);
        return PayrollCalculator.computeSummary(payrolls);
    }

    @Override
    public SummaryResponse getSummaryByYear(int year) {
        List<Payroll> payrolls = payrollRepository.findByPayYear(year);
        if (payrolls.isEmpty()) {
            throw new ResourceNotFoundException("No payroll summary found for year " + year);
        }
        payrollService.fetchEmployeeData(payrolls);
        return PayrollCalculator.computeSummary(payrolls);
    }

    @Override
    public SummaryResponse getSummaryByYearAndMonth(YearMonth yearMonth) {
        String month = yearMonth.getMonth().name();
        int year = yearMonth.getYear();
        List<Payroll> payrolls = payrollRepository.findByPayMonthAndPayYear(month, year);
        if (payrolls.isEmpty()) {
            throw new ResourceNotFoundException("No payroll summary found for month " + month + " and year " + year);
        }
        payrollService.fetchEmployeeData(payrolls);
        return PayrollCalculator.computeSummary(payrolls);
    }

    @Override
    public SummaryResponse getSummaryByDateRanges(YearMonth startYM, YearMonth endYM) {
        List<String> yearMonths = payrollService.extractYearMonth(startYM, endYM);
        List<Payroll> payrolls = payrollRepository.findByPayDateBetween(yearMonths.get(0),yearMonths.get(1));
        if (payrolls.isEmpty()) {
            throw new ResourceNotFoundException("No payroll summary found for date ranges " + yearMonths.get(0) + " to "+yearMonths.get(1));
        }
        payrollService.fetchEmployeeData(payrolls);
        return PayrollCalculator.computeSummary(payrolls);
    }
}
