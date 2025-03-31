package co.uk.simpleleger.ledger.service;


import co.uk.simpleleger.ledger.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
    }

    @Test
    void shouldCreateNewAccount() {
        Account account = accountService.createAccount("1", "Sam");
        assertEquals("1", account.getId());
        assertEquals("Sam", account.getName());
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void shouldThrowWhenCreatingDuplicateAccount() {
        accountService.createAccount("1", "Sam");
        assertThrows(IllegalArgumentException.class,
                () -> accountService.createAccount("1", "John"));
    }

    @Test
    void shouldFetchAllAccounts() {
        accountService.createAccount("1", "Sam");
        accountService.createAccount("2", "John");
        List<Account> accounts = accountService.getAllAccounts();
        assertEquals(2, accounts.size());
    }

    @Test
    void shouldUpdateBalance() {
        accountService.createAccount("1", "Sam");
        accountService.updateBalance("1", 200.0);
        assertEquals(200.0, accountService.getAccount("1").getBalance());
    }
}
