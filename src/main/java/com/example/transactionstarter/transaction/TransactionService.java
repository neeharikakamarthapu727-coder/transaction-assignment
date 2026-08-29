package com.example.transactionstarter.transaction;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction createTransaction(Transaction transaction) {

        if (transaction.getTransactionId() == null ||
                transaction.getTransactionId().isBlank()) {
            throw new IllegalArgumentException("Transaction ID is required");
        }

        if (transaction.getCustomerId() == null ||
                transaction.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("Customer ID is required");
        }

        if (transaction.getAmount() == null ||
                transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (repository.findById(transaction.getTransactionId()).isPresent()) {
            throw new IllegalArgumentException("Transaction ID already exists");
        }

        repository.save(transaction);

        return transaction;
    }

    public Transaction getTransaction(String transactionId) {
        return repository.findById(transactionId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Transaction not found"));
    }

    public List<Transaction> getCustomerTransactions(String customerId) {
        return repository.findByCustomerId(customerId);
    }

    public Transaction updateStatus(String transactionId, String newStatus) {

        Transaction transaction = getTransaction(transactionId);

        transaction.setTransactionStatus(newStatus);

        return transaction;
    }
}