package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ResponseAccountDto {
    private UUID accountId;
    private String status;
    private BigDecimal balance;
}
