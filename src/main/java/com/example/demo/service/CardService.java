package com.example.demo.service;

import com.example.demo.dto.ResponseCardDto;
import com.example.demo.dto.RequestCardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.UUID;

public interface CardService {

    Page<ResponseCardDto> getAllCards(Pageable pageable);

    ResponseCardDto getCardById(UUID cardId);

    ResponseCardDto createCard(RequestCardDto cardDto);

    void renewCard(UUID cardId, Date expireDate);

    void invertCardStatus(UUID cardId);

    void validateCard(UUID cardId);

    void deleteCard(UUID cardId);
}
