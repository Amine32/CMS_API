package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class RequestTransactionDto {
    private UUID cardId;
    private BigDecimal amount;
    private String transactionType;
}
