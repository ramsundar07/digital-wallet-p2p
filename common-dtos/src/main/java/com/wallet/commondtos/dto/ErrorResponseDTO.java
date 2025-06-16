package com.wallet.commondtos.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private String apiPath;
    private int errorCode;
    private String errorMsg;
    private LocalDateTime timeStamp;
    private String transactionReferenceId;
    public ErrorResponseDTO(String apiPath, int errorCode, String errorMsg, LocalDateTime errorTime) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.timeStamp = errorTime;
    }
    public ErrorResponseDTO(String apiPath, int errorCode, Map<String, String> validationErrors, LocalDateTime errorTime) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.timeStamp = errorTime;
        // Convert the map to a readable string format for errorMsg
        this.errorMsg = validationErrors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(java.util.stream.Collectors.joining("; "));
        if (this.errorMsg.isEmpty()) {
            this.errorMsg = "Validation failed for one or more fields.";
        } else {
            this.errorMsg = "Validation failed: " + this.errorMsg;
        }
    }

}
