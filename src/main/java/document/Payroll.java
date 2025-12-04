package document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@AllArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "payrolls")
public class Payroll {
    @Id
    private String id;

    @JsonIgnore
    @Indexed
    private String employeeId;

    private String payDate;
    private Double grossSalary;
    private Double taxAmount;
    private Double netSalary;

    @JsonIgnore
    private String payMonth;

    @JsonIgnore
    private Integer payYear;

    @Transient
    private Employee employee;

}
