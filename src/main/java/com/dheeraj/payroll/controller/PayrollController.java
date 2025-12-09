package com.dheeraj.payroll.controller;

import com.dheeraj.payroll.document.Payroll;
import com.dheeraj.payroll.dto.request.PayrollRequest;
import com.dheeraj.payroll.dto.response.ErrorResponse;
import com.dheeraj.payroll.service.PayrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Tag(name = "Payroll")
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    private final PayrollService payrollService;

    /*------------------Create payroll----------------*/
    @Operation(
            summary = "Create a payroll",
            description = "Providing employee id and pay date to create payroll"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
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
    @PostMapping
    public ResponseEntity<Payroll> createPayroll(@Valid @RequestBody PayrollRequest payrollRequest) {
        LocalDate payDate = LocalDate.parse(payrollRequest.getPayDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(payrollService.createPayroll(payrollRequest.getEmployeeId(), payDate));
    }


    /*------------------All payroll----------------*/
    @Operation(
            summary = "Get all payrolls",
            description = "Fetches all payroll"
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
    public ResponseEntity<List<Payroll>> getAllPayroll() {
        return ResponseEntity.ok(payrollService.getAllPayroll());
    }


    /*-------Get a employee payroll by employee id--------*/
    @Operation(
            summary = "Get a payroll by using employee id",
            description = "Displays payroll based on employee details"
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
    @GetMapping("employee/{employeeId}")
    public ResponseEntity<List<Payroll>> getEmployeePayrollsByEmployeeId(@Valid @PathVariable String employeeId) {
        return ResponseEntity.ok(payrollService.getEmployeePayrollsByEmployeeId(employeeId));
    }


    /*-------Get a employee payroll with year--------*/
    @Operation(
            summary = "Get a payroll of the employee by year",
            description = "Displays the employee payroll respective year"
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
    @GetMapping("employee/{employeeId}/year/{year}")
    public ResponseEntity<List<Payroll>> getEmployeePayrollsByYear(@Valid @PathVariable String employeeId, @PathVariable int year) {
        return ResponseEntity.ok(payrollService.getEmployeePayrollsByYear(employeeId,year));
    }


    /*---Get a employee payroll with year and month---*/
    @Operation(
            summary = "Get a payroll of the employee by respective year and month",
            description = "Display the employee payroll records based on the respective year and month"
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
    @GetMapping("/employee/{employeeId}/year-month/{yearMonth}")
    public ResponseEntity<List<Payroll>> getEmployeePayrollsByYearAndMonth(
            @Valid @PathVariable String employeeId, @PathVariable String yearMonth
    ) {
        return ResponseEntity.ok(payrollService.getEmployeePayrollsByYearAndMonth(employeeId, YearMonth.parse(yearMonth)));
    }


    /*-------Get a employee payroll dates range--------*/
    @Operation(
            summary = "Get a payroll of the employee period range date",
            description = "Displays employee payroll respective period range date"
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
    @GetMapping("/employee/{employeeId}/dates-range")
    public ResponseEntity<List<Payroll>> getEmployeePayrollsByDatesRange(
            @Valid @PathVariable String employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ){
        return ResponseEntity.ok(payrollService.getEmployeePayrollsByDatesRange(employeeId, YearMonth.parse(startDate), YearMonth.parse(endDate)));
    }

}
