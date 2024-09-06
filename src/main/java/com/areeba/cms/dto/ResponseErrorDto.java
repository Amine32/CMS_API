package com.areeba.cms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ResponseErrorDto {

    @Schema(description = "Path of the API endpoint that caused the error", example = "/api/accounts/{id}")
    private String path;

    @Schema(description = "Error type", example = "string")
    private String error;

    @Schema(description = "Detailed error message", example = "string")
    private String message;

    @Schema(description = "Timestamp when the error occurred", example = "2024-09-06T09:07:21.9951168")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;
}
