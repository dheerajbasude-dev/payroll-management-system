package com.dheeraj.payroll.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
    @NonNull private final int status;
    @NonNull private final String error;
    @NonNull private final String message;
    @NonNull private final String path;
    @NonNull private final String traceId;


}
