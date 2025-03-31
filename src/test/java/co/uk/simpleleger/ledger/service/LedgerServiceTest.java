package co.uk.simpleleger.ledger.service;


import co.uk.simpleleger.ledger.model.Account;
import co.uk.simpleleger.ledger.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LedgerServiceTest {

    private LedgerService ledgerService;

    @BeforeEach
    void setUp() {
        AccountService accountService = new AccountService();
        ledgerService = new LedgerService(accountService);
        Account acc = accountService.createAccount("acc1", "Alice");
    }

    @Test
    void shouldDepositAmount() {
        ledgerService.deposit("acc1", 100.0);
        assertEquals(100.0, ledgerService.getBalance("acc1"));
    }

    @Test
    void shouldWithdrawAmount() {
        ledgerService.deposit("acc1", 200.0);
        ledgerService.withdraw("acc1", 50.0);
        assertEquals(150.0, ledgerService.getBalance("acc1"));
    }

    @Test
    void shouldThrowOnInsufficientFunds() {
        assertThrows(IllegalArgumentException.class, () -> ledgerService.withdraw("acc1", 100.0));
    }


    @Test
    void shouldReturnTransactionHistory() {
        ledgerService.deposit("acc1", 100.0);
        ledgerService.withdraw("acc1", 30.0);
        List<Transaction> transactions = ledgerService.getTransactions("acc1");
        assertEquals(2, transactions.size());
    }
}
