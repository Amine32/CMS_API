package com.areeba.cms.controller;

import com.areeba.cms.dto.RequestTransactionDto;
import com.areeba.cms.dto.ResponseErrorDto;
import com.areeba.cms.dto.ResponseTransactionDto;
import com.areeba.cms.service.TransactionImpService;
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

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction API")
public class TransactionController {

    private final TransactionImpService transactionService;

    @Operation(summary = "Get transactions by card ID", description = "Retrieve a paginated list of transactions for the specified card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @GetMapping("/byCard/{cardId}")
    public Page<ResponseTransactionDto> getTransactionsByCardId(
            @Parameter(description = "ID of the card") @PathVariable UUID cardId,
            @Parameter(description = "Page number") @RequestParam(required = false) Integer page,
            @Parameter(description = "Size of the page") @RequestParam(required = false) Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        return transactionService.getTransactionsByCardId(cardId, pageable);
    }

    @Operation(summary = "Get transaction by ID", description = "Retrieve transaction information by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transaction"),
            @ApiResponse(responseCode = "404", description = "Transaction not found with provided ID",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @GetMapping("/{transactionId}")
    public ResponseTransactionDto getTransactionById(
            @Parameter(description = "ID of the transaction") @PathVariable UUID transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @Operation(summary = "Create a new transaction", description = "Create a new transaction with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body or business rule violations",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Card or Account not found",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTransactionDto createTransaction(
            @RequestBody RequestTransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }
}
