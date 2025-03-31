package co.uk.simpleleger.ledger.service;

import co.uk.simpleleger.ledger.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Handles account management.
 * This class helps to add, get, update, and delete accounts.
 */
@Service
public class AccountService {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    /**
     * Creates a new account.
     *
     * @param id   unique  ID
     * @param name name of the account owner
     * @return account created
     * @throws IllegalArgumentException if the ID already exists
     */
    public Account createAccount(String id, String name) {
        if (accounts.containsKey(id)) {
            throw new IllegalArgumentException("Account ID already exists : " + id);
        }
        Account account = new Account(id, name, 0.0);
        accounts.put(id, account);
        return account;
    }

    /**
     * Returns a list of all existing accounts.
     *
     * @return Accounts list
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    /**
     * returns account by ID.
     *
     * @param id account id
     * @return account for the given ID
     * @throws IllegalArgumentException if the account does not exist
     */

    public Account getAccount(String id) {
        if (!accounts.containsKey(id)) {
            throw new IllegalArgumentException("No account found for this ID : " + id);
        }
        return accounts.get(id);
    }

    /**
     * Update balance of account.
     *
     * @param id         account ID
     * @param newBalance updated balance amount
     */
    public void updateBalance(String id, double newBalance) {
        Account account = accounts.get(id);
        account.setBalance(newBalance);
    }


    /**
     * Removes an account with the given id.
     *
     * @param accountId ID of account to be removed
     * @throws IllegalArgumentException if the account doesn't exist
     */
    public void deleteAccount(String accountId) {
        if (!accounts.containsKey(accountId)) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        accounts.remove(accountId);
    }


}
