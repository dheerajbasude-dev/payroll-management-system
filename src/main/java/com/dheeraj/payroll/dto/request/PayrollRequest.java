package com.dheeraj.payroll.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(
        name = "PayrollRequest",
        description = "Schema to hold payroll request information"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PayrollRequest {
    /*---------------EmployeeId-----------------*/
    @Schema(
            description = "Employee id",
            example = "692c88b1f4919613b8e180c2"
    )
    @NotBlank(message = "Employee id is must be required")
    private String employeeId;


    /*-----------------PayDate------------------*/
    @Schema(
            description = "Payroll date",
            example = "2025-11-29"
    )
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Pay date must be in yyyy-MM-dd format"
    )
    @NotBlank(message = "Pay date is must be required")
    private String payDate;
}
