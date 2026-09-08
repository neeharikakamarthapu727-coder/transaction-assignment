package com.example.transactionstarter.transaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateTransactionRequest {

    @NotBlank(message = "Transaction ID is required")
    @Size(max = 64, message = "Transaction ID must not exceed 64 characters")
    private String transactionId;

    @NotBlank(message = "Customer ID is required")
    @Size(max = 64, message = "Customer ID must not exceed 64 characters")
    private String customerId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @Digits(integer = 12, fraction = 2,
            message = "Amount must have at most 2 decimal places")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency must be a 3-letter uppercase code"
    )
    private String currency;

    @NotBlank(message = "Transaction type is required")
    @Pattern(
            regexp = "^(PAYMENT|REFUND|TRANSFER)$",
            message = "Transaction type must be PAYMENT, REFUND, or TRANSFER"
    )
    private String transactionType;

    @NotBlank(message = "Transaction status is required")
    @Pattern(
            regexp = "^(PENDING|COMPLETED|FAILED)$",
            message = "Transaction status must be PENDING, COMPLETED, or FAILED"
    )
    private String transactionStatus;

    public String getTransactionId() {
        return transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }
}