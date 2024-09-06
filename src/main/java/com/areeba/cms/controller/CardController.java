package com.areeba.cms.controller;

import com.areeba.cms.dto.ResponseCardDto;
import com.areeba.cms.dto.RequestCardDto;
import com.areeba.cms.dto.ResponseErrorDto;
import com.areeba.cms.service.CardImpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@Tag(name = "Card API", description = "Operations related to Cards")
public class CardController {

    private final CardImpService cardService;

    @Operation(summary = "Get all cards", description = "Retrieve a paginated list of all cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cards"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @GetMapping("/all")
    public Page<ResponseCardDto> getAllCards(
            @Parameter(description = "Page number") @RequestParam(required = false) Integer page,
            @Parameter(description = "Size of the page") @RequestParam(required = false) Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        return cardService.getAllCards(pageable);
    }

    @Operation(summary = "Get card by ID", description = "Retrieve card information by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved card"),
            @ApiResponse(responseCode = "404", description = "Card not found with provided ID",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @GetMapping("/{cardId}")
    public ResponseCardDto getCardById(
            @Parameter(description = "ID of the card to retrieve") @PathVariable UUID cardId) {
        return cardService.getCardById(cardId);
    }

    @Operation(summary = "Create a new card", description = "Create a new card with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Account not found with provided ID",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))

    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCardDto createCard(@RequestBody RequestCardDto cardDto) {
        return cardService.createCard(cardDto);
    }

    @Operation(summary = "Renew card expiry date", description = "Renew the expiry date of the card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully renewed card"),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @PatchMapping("/renew/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void renewCard(
            @Parameter(description = "ID of the card to renew") @PathVariable UUID cardId,
            @RequestBody Date expireDate) {
        cardService.renewCard(cardId, expireDate);
    }

    @Operation(summary = "Invert card status", description = "Activate or deactivate a card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully inverted card status"),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @PatchMapping("/invertActivation/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invertCardStatus(
            @Parameter(description = "ID of the card to invert status") @PathVariable UUID cardId) {
        cardService.invertCardStatus(cardId);
    }

    @Operation(summary = "Delete a card", description = "Delete the card with the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted card"),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(
            @Parameter(description = "ID of the card to delete") @PathVariable UUID cardId) {
        cardService.deleteCard(cardId);
    }
}
