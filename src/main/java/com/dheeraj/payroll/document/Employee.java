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
@AllArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;

    private String name;
    private Integer age;
    private String gender;
    private String designation;
    private Integer rating;
    private Double salary;

}
