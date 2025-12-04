package com.dheeraj.payroll.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Schema(
        name = "EmployeeRequest",
        description = "Schema to hold Employee request information"
)

@NoArgsConstructor
@Getter
@ToString
public class EmployeeRequest {

    /*-----------------Name-----------------*/
    @Schema(
            description = "Name of the Employee",
            example = "Disha Patani"
    )
    @Size(min = 4, message = "Name must have at least 4 characters")
    @NotBlank(message = "Name must be required")
    private String name;


    /*-----------------Age-----------------*/
    @Schema(
           description = "Age of the employee in years",
           example = "31"
    )
    @Max(value = 60, message = "Age cannot exceed 60 years")
    @Min(value = 21, message = "Age must have at least 21 years")
    @NotNull(message = "Age must be required")
    private Integer age;


    /*--------------Gender------------------*/
    @Schema(
            description = "Gender of the employee",
            example = "Female"
    )
    @Size(min = 4, message = "Gender must have at least 4 characters")
    @NotBlank(message = "Gender is must be required")
    private String gender;


    /*------------Designation---------------*/
    @Schema(
            description = "Designation of the employee",
            example = "Senior Developer"
    )
    @Size(min=7, message = "Designation must have at least 7 characters")
    @NotBlank(message = "Designation must be required")
    private String designation;


    /*---------------Rating-----------------*/
    @Schema(
            description = "Rating to the employee",
            example = "5"
    )
    @NotNull(message = "Rating must be required")
    private Integer rating;

}
