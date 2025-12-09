package com.dheeraj.payroll.dto.response;

import com.dheeraj.payroll.document.Payroll;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Schema(
        name = "SummaryResponse",
        description = "Schema to hold Summary response information"
)

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class SummaryResponse {

    private List<Payroll> payrolls;

    private Double totalGrossSalary;

    private Double totalTaxAmount;

    private Double totalNetSalary;

}
