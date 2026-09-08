package com.example.transactionstarter.transaction;

import jakarta.validation.constraints.NotBlank;

public class UpdateTransactionStatusRequest {

    @NotBlank(message = "Transaction status is required")
    private String status;

    public String getStatus() {
        return status;
    }
}