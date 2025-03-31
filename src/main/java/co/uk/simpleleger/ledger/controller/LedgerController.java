package co.uk.simpleleger.ledger.controller;


import co.uk.simpleleger.ledger.model.Transaction;
import co.uk.simpleleger.ledger.service.LedgerService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }



    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<String> deposit(@PathVariable @NotBlank String accountId, @RequestParam("amount") @Positive double amount) {
        ledgerService.deposit(accountId, amount);
        return ResponseEntity.ok("Deposited " + amount + " to account " + accountId);
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<String> withdraw(@PathVariable @NotBlank String accountId, @RequestParam("amount") @Positive double amount) {
        ledgerService.withdraw(accountId, amount);
        return ResponseEntity.ok("Withdrew " + amount + " from account " + accountId);
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<Double> getBalance(@PathVariable @NotBlank String accountId) {
        return ResponseEntity.ok(ledgerService.getBalance(accountId));
    }

    @GetMapping("/transactions/history/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable @NotBlank String accountId) {
        return ResponseEntity.ok(ledgerService.getTransactions(accountId));
    }
}
