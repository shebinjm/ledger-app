package co.uk.simpleleger.ledger.controller;


import co.uk.simpleleger.ledger.model.Account;
import co.uk.simpleleger.ledger.service.AccountService;
import co.uk.simpleleger.ledger.service.LedgerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing account-related operations such as creation, listing, and deletion.
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final LedgerService ledgerService;

    public AccountController(AccountService accountService, LedgerService ledgerService) {
        this.accountService = accountService;
        this.ledgerService = ledgerService;
    }

    /**
     * Creates new account.
     *
     * @param accountRequest the account details
     * @return the created account
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account accountRequest) {
        Account account = accountService.createAccount(accountRequest.getId(), accountRequest.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    /**
     * Retrieves all accounts.
     *
     * @return list of accounts
     */
    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        try {
            return ResponseEntity.ok(accountService.getAllAccounts());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve accounts: " + ex.getMessage());
        }

    }

    /**
     * Deletes an account and its associated transactions.
     *
     * @param accountId the account ID
     * @return confirmation message
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable @NotBlank String accountId) {
        accountService.deleteAccount(accountId);
        ledgerService.deleteLedgerForAccount(accountId);
        return ResponseEntity.ok("Account and associated transactions deleted for accountId: " + accountId);
    }
}