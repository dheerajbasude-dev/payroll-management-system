package com.dheeraj.payroll.controller;

import com.dheeraj.payroll.dto.response.ErrorResponse;
import com.dheeraj.payroll.dto.response.SummaryResponse;
import com.dheeraj.payroll.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@Tag(name = "Summary")
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("api/summary")
public class SummaryController {

    private final SummaryService summaryService;

    /*---------------------Fetch all summaries------------------------*/
    @Operation(
            summary = "Fetch all summaries",
            description = "Fetches all summary details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Unauthorize error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<SummaryResponse> getAllSummary() {
        return ResponseEntity.ok(summaryService.getAllSummary());
    }


    /*------------------------Get summary by year---------------------*/
    @Operation(
            summary = "Get a summary by respective year",
            description = "Displays the summary by respective year only"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Unauthorize error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not found error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/year/{year}")
    public ResponseEntity<SummaryResponse> getSummaryByYear(@Valid @PathVariable int year) {
        return ResponseEntity.ok(summaryService.getSummaryByYear(year));
    }


    /*-------------------Get summary by year and month-----------------*/
    @Operation(
            summary = "Get a summary by respective year and month",
            description = "Displays the summary by respective year and month"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Unauthorize error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not found error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/year-month/{yearMonth}")
    public ResponseEntity<SummaryResponse> getSummaryByYearAndMonth(@Valid @PathVariable String yearMonth){
        return ResponseEntity.ok(summaryService.getSummaryByYearAndMonth(YearMonth.parse(yearMonth)));
    }


    /*-------------------Get summary date ranges-----------------*/
    @Operation(
            summary = "Get a summary by date ranges",
            description = "Displays the summary by range date"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Unauthorize error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not found error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/date-ranges")
    public ResponseEntity<SummaryResponse> getSummaryByDateRanges(@Valid @RequestParam String startYM, @RequestParam String endYM) {
        return ResponseEntity.ok(summaryService.getSummaryByDateRanges(YearMonth.parse(startYM), YearMonth.parse(endYM)));
    }

}
