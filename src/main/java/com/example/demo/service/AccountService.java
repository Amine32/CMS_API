package com.example.demo.service;

import com.example.demo.dto.ResponseAccountDto;
import com.example.demo.model.Card;
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
