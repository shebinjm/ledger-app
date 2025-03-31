package co.uk.simpleleger.ledger.service;

import co.uk.simpleleger.ledger.model.Account;
import co.uk.simpleleger.ledger.model.Transaction;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles transaction operations:  deposit, withdrawal, and transaction retrieval.
 */
@Service
public class LedgerService {

    private final Map<String, List<Transaction>> ledger = new ConcurrentHashMap<>();

    private final AccountService accountService;

    public LedgerService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Deposits the given amount and initializes the ledger if needed.
     *
     * @param accountId the account ID
     * @param amount    the amount to deposit
     */
    public void deposit(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be greater than zero");
        }

        Account account = accountService.getAccount(accountId); // Get the account object

        // Lazy initialize ledger if not present
        ledger.computeIfAbsent(accountId, k -> new ArrayList<>());

        ledger.get(accountId).add(new Transaction(accountId, amount, "deposit"));
        double newBalance = account.getBalance() + amount;
        accountService.updateBalance(accountId, newBalance);
    }

    /**
     * Withdraws the given amount from account.
     *
     * @param accountId the account ID
     * @param amount    the amount to withdraw
     */
    public void withdraw(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be greater than zero");
        }

        Account account = accountService.getAccount(accountId); // Get the account object

        if (!ledger.containsKey(accountId)) {
            throw new IllegalArgumentException("No transactions found for account: " + accountId);
        }

        double currentBalance = account.getBalance();
        if (currentBalance < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        ledger.get(accountId).add(new Transaction(accountId, -amount, "withdrawal"));
        accountService.updateBalance(accountId, currentBalance - amount);
    }

    /**
     * Returns the current balance for the account id.
     *
     * @param accountId the account ID
     * @return the balance
     */
    public double getBalance(String accountId) {
        return accountService.getAccount(accountId).getBalance();
    }

    /**
     * Return transactions for the given account id.
     *
     * @param accountId the account ID
     * @return transactions list
     */
    public List<Transaction> getTransactions(String accountId) {
        accountService.getAccount(accountId); // Validate existence
        return Collections.unmodifiableList(ledger.get(accountId));
    }


    /**
     * Deletes the ledger for the account id.
     *
     * @param accountId the account ID
     */
    public void deleteLedgerForAccount(String accountId) {
        ledger.remove(accountId);
    }


}
