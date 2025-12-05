package com.dheeraj.payroll.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Schema(
        name = "Payroll",
        description = "Schema to hold Payroll information"
)

@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "payrolls")
public class Payroll {
    @Id
    private String id;

    @JsonIgnore
    @Indexed
    @NonNull private String employeeId;

    @NonNull private String payDate;
    @NonNull private Double grossSalary;
    @NonNull private Double taxAmount;
    @NonNull private Double netSalary;

    @JsonIgnore
    @NonNull private String payMonth;

    @JsonIgnore
    @NonNull private Integer payYear;

    @Transient
    private Employee employee;

}
