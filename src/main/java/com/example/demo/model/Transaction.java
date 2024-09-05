package com.example.demo.model;

import com.example.demo.common.Enums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(schema = "cms")
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @NotNull(message = "Card is mandatory")
    private Card card;

    @NotNull(message = "Transaction amount is mandatory")
    @DecimalMin(value = "0.01", message = "Transaction amount must be greater than 0")
    private BigDecimal transactionAmount;

    @NotNull(message = "Transaction date is mandatory")
    private Timestamp transactionDate = new Timestamp(System.currentTimeMillis());

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transaction type is mandatory")
    private Enums.TransactionType transactionType;
}
