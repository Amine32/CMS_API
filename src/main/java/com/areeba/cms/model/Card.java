package com.areeba.cms.model;

import com.areeba.cms.common.Enums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(schema = "cms")
public class Card {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @NotNull(message = "Account is mandatory")
    private Account account;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is mandatory")
    private Enums.Status status = Enums.Status.INACTIVE;  // Default to INACTIVE

    @NotNull(message = "Expiry date is mandatory")
    @FutureOrPresent(message = "Expiry date must be in the future or present")
    private Date expiry = Date.from(LocalDate.now().plusYears(5).atStartOfDay(ZoneId.systemDefault()).toInstant());  // Default to 5 years from now

    @NotNull(message = "Card number is mandatory")
    @Column(unique = true, updatable = false)  // Ensure uniqueness and immutability
    private String cardNumber= generateRandomCardNumber();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    private String generateRandomCardNumber() {
        return new Random().ints(0, 10)
                .limit(16)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}