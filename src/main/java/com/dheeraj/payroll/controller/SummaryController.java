package com.dheeraj.payroll.controller;

import com.dheeraj.payroll.dto.response.SummaryResponse;
import com.dheeraj.payroll.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@Tag(name = "Summary")
@Validated
@RestController
@RequestMapping("api/summary")
public class SummaryController {

    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    /*---------------------Fetch all summaries------------------------*/
    @Operation(
            summary = "Fetch all summaries",
            description = "Fetches all summary details"
    )
    @GetMapping
    public ResponseEntity<SummaryResponse> getAllSummary() {
        return ResponseEntity.ok(summaryService.getAllSummary());
    }


    /*------------------------Get summary by year---------------------*/
    @Operation(
            summary = "Get a summary by respective year",
            description = "Displays the summary by respective year only"
    )
    @GetMapping("/year/{year}")
    public ResponseEntity<SummaryResponse> getSummaryByYear(@Valid @PathVariable int year) {
        return ResponseEntity.ok(summaryService.getSummaryByYear(year));
    }


    /*-------------------Get summary by year and month-----------------*/
    @Operation(
            summary = "Get a summary by respective year and month",
            description = "Displays the summary by respective year and month"
    )
    @GetMapping("/year-month/{yearMonth}")
    public ResponseEntity<SummaryResponse> getSummaryByYearAndMonth(@Valid @PathVariable String yearMonth){
        return ResponseEntity.ok(summaryService.getSummaryByYearAndMonth(YearMonth.parse(yearMonth)));
    }


    @Operation(
            summary = "Get a summary by date ranges",
            description = "Displays the summary by range date"
    )
    /*-------------------Get summary date ranges-----------------*/
    @GetMapping("/date-ranges")
    public ResponseEntity<SummaryResponse> getSummaryByDateRanges(@Valid @RequestParam String startYM, @RequestParam String endYM) {
        return ResponseEntity.ok(summaryService.getSummaryByDateRanges(YearMonth.parse(startYM), YearMonth.parse(endYM)));
    }

}
