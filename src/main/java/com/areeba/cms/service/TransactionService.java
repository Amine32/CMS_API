package com.areeba.cms.service;

import com.areeba.cms.dto.RequestTransactionCountDto;
import com.areeba.cms.dto.RequestTransactionDto;
import com.areeba.cms.dto.ResponseTransactionCountDto;
import com.areeba.cms.dto.ResponseTransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.UUID;

public interface TransactionService {

    Page<ResponseTransactionDto> getTransactionsByCardId(UUID cardId, Pageable pageable);

    ResponseTransactionDto getTransactionById(UUID transactionId);

    ResponseTransactionDto createTransaction(RequestTransactionDto transactionDto);

    ResponseTransactionCountDto getTransactionCountByCardIdAndTransactionDateBetween(RequestTransactionCountDto dto);
}
