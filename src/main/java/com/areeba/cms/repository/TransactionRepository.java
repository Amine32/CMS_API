package com.areeba.cms.repository;

import com.areeba.cms.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findAllByCardId(UUID cardId, Pageable pageable);
    int countByCardIdAndTransactionDateBetween(UUID card_id, Timestamp transactionDate, Timestamp transactionDate2);
}
