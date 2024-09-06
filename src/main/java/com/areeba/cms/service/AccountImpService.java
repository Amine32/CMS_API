package com.areeba.cms.service;

import com.areeba.cms.common.DtoConverter;
import com.areeba.cms.common.Enums;
import com.areeba.cms.dto.ResponseAccountDto;
import com.areeba.cms.exception.InsufficientFundsException;
import com.areeba.cms.exception.InvalidAccountException;
import com.areeba.cms.model.Account;
import com.areeba.cms.model.Card;
import com.areeba.cms.repository.AccountRepository;
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
public class AccountImpService implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseAccountDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(account -> DtoConverter.convert(account, ResponseAccountDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseAccountDto getAccountDtoById(UUID accountId) {
        return DtoConverter.convert(getAccountEntityById(accountId), ResponseAccountDto.class);
    }

    private Account getAccountEntityById(UUID accountId) {
        return EntityFetcher.getEntityById(accountId, accountRepository, "Account");
    }

    @Override
    @Transactional
    public ResponseAccountDto createAccount() {
        Account account = new Account();
        return DtoConverter.convert(accountRepository.save(account), ResponseAccountDto.class);
    }


    @Override
    @Transactional
    public void changeAccountBalance(UUID accountId, BigDecimal amount) {
        Account account = getAccountEntityById(accountId);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void validateAccount(UUID accountId, BigDecimal amount) {
        Account account = getAccountEntityById(accountId);
        if (!Enums.Status.ACTIVE.equals(account.getStatus())) {
            throw new InvalidAccountException("Account is inactive");
        } else if (amount.compareTo(account.getBalance()) > 0) {
            throw new InsufficientFundsException("Insufficient balance");
        }
    }

    @Override
    @Transactional
    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void invertAccountStatus(UUID accountId) {
        Account account = getAccountEntityById(accountId);
        account.setStatus(account.getStatus().equals(Enums.Status.ACTIVE)
                ? Enums.Status.INACTIVE
                : Enums.Status.ACTIVE);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void appendCardToAccount(Card card) {
        Account account = getAccountEntityById(card.getAccount().getId());
        account.getCards().add(card);
        accountRepository.save(account);
    }
}