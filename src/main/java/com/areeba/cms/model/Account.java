package com.areeba.cms.model;

import com.areeba.cms.common.Enums;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(schema = "cms")
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Card> cards;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is mandatory")
    private Enums.Status status = Enums.Status.INACTIVE;

    @NotNull(message = "Balance is mandatory")
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    private BigDecimal balance = BigDecimal.ZERO; // Default to 0
}
