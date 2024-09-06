package com.areeba.cms.service;

import com.areeba.cms.dto.ResponseAccountDto;
import com.areeba.cms.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {

    Page<ResponseAccountDto> getAllAccounts(Pageable pageable);

    ResponseAccountDto getAccountDtoById(UUID id);

    ResponseAccountDto createAccount();

    void changeAccountBalance(UUID id, BigDecimal amount);

    void validateAccount(UUID id, BigDecimal amount);

    void deleteAccount(UUID id);

    void invertAccountStatus(UUID id);

    void appendCardToAccount(Card card);
}
