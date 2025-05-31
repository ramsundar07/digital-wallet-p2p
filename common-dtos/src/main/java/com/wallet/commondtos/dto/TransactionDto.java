package com.wallet.commondtos.dto;

import com.wallet.commondtos.enums.TransactionCategory;
import com.wallet.commondtos.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long Id;
    private Long senderWalletId;
    private Long receiverWalletId;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private LocalDateTime timeStamp;
    private TransactionCategory transactionCategory;
}
