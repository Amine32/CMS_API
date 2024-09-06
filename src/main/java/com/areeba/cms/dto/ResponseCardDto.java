package com.areeba.cms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ResponseCardDto {
    private UUID cardId;
    private String status;
    private Date expiry;
    private String cardNumber;
    private UUID accountId;
}
