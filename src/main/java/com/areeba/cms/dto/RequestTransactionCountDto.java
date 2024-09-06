package com.areeba.cms.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
//TODO PLACE IN COMMON LIBRARY OR PACKAGE
public class RequestTransactionCountDto {
    private UUID cardId;
    private Timestamp startDate;
    private Timestamp endDate;
}
