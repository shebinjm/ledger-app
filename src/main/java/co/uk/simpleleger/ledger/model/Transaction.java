package co.uk.simpleleger.ledger.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    @NotBlank(message = "Account ID must not be blank")
    private String accountId;

    @NotNull(message = "Transaction amount is required")
    private Double amount;

    @NotBlank(message = "Transaction type must not be blank")
    private String mode;

    private LocalDateTime time;

    public Transaction(String accountId, double amount, String mode){
        this.accountId =accountId;
        this.amount=amount;
        this.mode = mode;
        this.time = LocalDateTime.now();
    }

    public String getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMode() {
        return mode;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0
                && Objects.equals(accountId, that.accountId)
                && Objects.equals(mode, that.mode)
                && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, amount, mode, time);
    }
}
