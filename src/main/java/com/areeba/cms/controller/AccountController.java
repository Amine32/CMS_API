package com.areeba.cms.controller;

import com.areeba.cms.dto.ResponseAccountDto;
import com.areeba.cms.dto.ResponseErrorDto;
import com.areeba.cms.service.AccountImpService;
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
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account API", description = "Operations related to Accounts")
public class AccountController {

    private final AccountImpService accountService;

    @Operation(summary = "Get all accounts", description = "Retrieve a paginated list of all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved accounts"),
            @ApiResponse(responseCode = "400", description = "Invalid page or size parameters",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @GetMapping("/all")
    public Page<ResponseAccountDto> getAllAccounts(
            @Parameter(description = "Page number") @RequestParam(required = false) Integer page,
            @Parameter(description = "Size of the page") @RequestParam(required = false) Integer size) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10);
        return accountService.getAllAccounts(pageable);
    }

    @Operation(summary = "Get account by ID", description = "Retrieve account information by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved account"),
            @ApiResponse(responseCode = "404", description = "Account not found with provided ID",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @GetMapping("/{accountId}")
    public ResponseAccountDto getAccountById(
            @Parameter(description = "ID of the account to retrieve") @PathVariable UUID accountId) {
        return accountService.getAccountDtoById(accountId);
    }

    @Operation(summary = "Create a new account", description = "Create a new account with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseAccountDto createAccount() {
        return accountService.createAccount();
    }

    @Operation(summary = "Delete an account", description = "Delete the account with the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted account"),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @Parameter(description = "ID of the account to delete") @PathVariable UUID accountId) {
        accountService.deleteAccount(accountId);
    }

    @Operation(summary = "Invert account status", description = "Activate or deactivate an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully inverted account status"),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))
    })
    @PatchMapping("/invertActivation/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invertAccountStatus(
            @Parameter(description = "ID of the account to invert status") @PathVariable UUID accountId) {
        accountService.invertAccountStatus(accountId);
    }
}
