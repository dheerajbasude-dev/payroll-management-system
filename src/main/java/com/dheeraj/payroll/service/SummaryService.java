package com.dheeraj.payroll.service;

import com.dheeraj.payroll.dto.response.SummaryResponse;

import java.time.YearMonth;

public interface SummaryService {

    SummaryResponse getAllSummary();

    SummaryResponse getSummaryByYear(int year);

    SummaryResponse getSummaryByYearAndMonth(YearMonth parse);

    SummaryResponse getSummaryByDateRanges(YearMonth parse, YearMonth parse1);
}
