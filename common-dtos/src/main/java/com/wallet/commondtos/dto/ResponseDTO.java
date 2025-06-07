package com.wallet.commondtos.dto;

public record ResponseDTO<T>(String statusCode, String statusMsg, T data) {

}

