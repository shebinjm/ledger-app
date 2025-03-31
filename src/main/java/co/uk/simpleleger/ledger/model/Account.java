package co.uk.simpleleger.ledger.model;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class Account {

    @NotBlank(message = "Account ID must not be blank")
    private String id;
    @NotBlank(message = "name must not be blank")
    private String name;
    private double balance;

    public Account(String id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(balance, account.balance) == 0
                && Objects.equals(id, account.id)
                && Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }
}
