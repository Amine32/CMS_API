package com.areeba.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RequestFraudCheckDto {
    private UUID cardId;
    private UUID transactionId;
    private BigDecimal amount;
}
