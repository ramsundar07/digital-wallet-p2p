package com.wallet.commondtos.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private String apiPath;
    private int errorCode;
    private Object errorMsg;
    private LocalDateTime timeStamp;

}
