package com.areeba.cms.service;

import com.areeba.cms.common.DtoConverter;
import com.areeba.cms.common.Enums;
import com.areeba.cms.dto.RequestTransactionDto;
import com.areeba.cms.dto.ResponseTransactionDto;
import com.areeba.cms.model.Transaction;
import com.areeba.cms.repository.TransactionRepository;
import com.areeba.cms.common.EntityFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionImpService implements TransactionService {

    public final TransactionRepository transactionRepository;
    public final AccountImpService accountService;
    public final CardImpService cardService;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseTransactionDto> getTransactionsByCardId(UUID cardId, Pageable pageable) {
        return transactionRepository.findAllByCardId(cardId, pageable)
                .map(transaction -> DtoConverter.convert(transaction, ResponseTransactionDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseTransactionDto getTransactionById(UUID transactionId) {
        return DtoConverter.convert(getTransactionEntityById(transactionId), ResponseTransactionDto.class);
    }

    private Transaction getTransactionEntityById(UUID transactionId) {
        return EntityFetcher.getEntityById(transactionId, transactionRepository, "Transaction");
    }

    @Override
    @Transactional
    public ResponseTransactionDto createTransaction(RequestTransactionDto transactionDto) {
        cardService.validateCard(transactionDto.getCardId());

        UUID accountId = cardService.getCardById(transactionDto.getCardId()).getAccountId();
        //Configures amount sign according to transaction type
        BigDecimal transactionAmount = transactionDto.getTransactionType().equals(Enums.TransactionType.D.toString())
                ? transactionDto.getAmount().negate()
                : transactionDto.getAmount();

        //Checks if account is active and has enough funds if debit transaction
        accountService.validateAccount(accountId, transactionDto.getTransactionType().equals(Enums.TransactionType.D.toString())
                ? transactionDto.getAmount()
                : BigDecimal.valueOf(0));

        accountService.changeAccountBalance(accountId, transactionAmount);

        Transaction transaction = DtoConverter.convert(transactionDto, Transaction.class);
        return DtoConverter.convert(transactionRepository.save(transaction), ResponseTransactionDto.class);
    }
}