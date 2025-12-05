package com.dheeraj.payroll.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Schema(
        name = "Employee",
        description = "Schema to hold Employee information"
)
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;

    @NonNull private String name;
    @NonNull private Integer age;
    @NonNull private String gender;
    @NonNull private String designation;
    @NonNull private Integer rating;
    @NonNull private Double salary;

}
