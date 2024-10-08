package com.example.demo.service;

import com.example.demo.common.DtoConverter;
import com.example.demo.common.Enums;
import com.example.demo.dto.ResponseCardDto;
import com.example.demo.dto.RequestCardDto;
import com.example.demo.exception.InvalidAccountException;
import com.example.demo.exception.InvalidCardException;
import com.example.demo.model.Card;
import com.example.demo.repository.CardRepository;
import com.example.demo.common.EntityFetcher;
import com.example.demo.security.AESEncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardImpService implements CardService {

    private final CardRepository cardRepository;
    private final AccountImpService accountService;
    private final AESEncryptionService encryptionService;

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseCardDto> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable).map(cardEntity -> DtoConverter.convert(cardEntity, ResponseCardDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseCardDto getCardById(UUID carId) {
        return DtoConverter.convert(getCardEntityById(carId), ResponseCardDto.class);
    }

    private Card getCardEntityById(UUID cardId) {
        return EntityFetcher.getEntityById(cardId, cardRepository, "Card");
    }

    @Override
    @Transactional
    public ResponseCardDto createCard(RequestCardDto cardDto) {
        Card card = DtoConverter.convert(cardDto, Card.class);
        if(Objects.equals(accountService.getAccountDtoById(cardDto.getAccountId()).getStatus(), Enums.Status.INACTIVE.toString())) {
            throw new InvalidAccountException("Account is not active");
        }

        try {
            card.setCardNumber(encryptionService.encrypt(card.getCardNumber()));
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting card number", e);
        }

        card = cardRepository.save(card);

        accountService.appendCardToAccount(card);

        return DtoConverter.convert(card, ResponseCardDto.class);
    }

    @Override
    @Transactional
    public void renewCard(UUID cardId, Date expireDate) {
        Card card = getCardEntityById(cardId);
        card.setExpiry(expireDate);
        cardRepository.save(card);
    }

    @Override
    @Transactional
    public void invertCardStatus(UUID carId) {
        Card card = getCardEntityById(carId);
        card.setStatus(card.getStatus().equals(Enums.Status.ACTIVE)
                ? Enums.Status.INACTIVE
                : Enums.Status.ACTIVE);
        cardRepository.save(card);
    }

    @Override
    @Transactional
    public void validateCard(UUID carId) {
        Card card = getCardEntityById(carId);
        if (!(Enums.Status.ACTIVE.equals(card.getStatus()) && card.getExpiry().after(new Date())))
            throw new InvalidCardException("Invalid or inactive card");
    }

    @Override
    @Transactional
    public void deleteCard(UUID id) {
        cardRepository.deleteById(id);
    }
}
