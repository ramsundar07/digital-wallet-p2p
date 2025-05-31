package com.wallet.commondtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletUpdateDto {

    private Long walletId;
    private BigDecimal amount;
    private String operation;
}
