package com.example.transactionstarter.transaction;

import java.math.BigDecimal;

public class Transaction {

    private String transactionId;
    private String customerId;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
    private String transactionStatus;

    public Transaction(String transactionId,
                       String customerId,
                       BigDecimal amount,
                       String currency,
                       String transactionType,
                       String transactionStatus) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
    }

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
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}