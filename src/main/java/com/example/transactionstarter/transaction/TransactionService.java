package com.example.transactionstarter.transaction;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    private static final Set<String> ALLOWED_STATUSES =
            Set.of("PENDING", "COMPLETED", "FAILED");

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

        if (transaction.getCurrency() == null ||
                transaction.getCurrency().isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }

        if (transaction.getTransactionType() == null ||
                transaction.getTransactionType().isBlank()) {
            throw new IllegalArgumentException("Transaction type is required");
        }

        if (transaction.getTransactionStatus() == null ||
                transaction.getTransactionStatus().isBlank()) {
            throw new IllegalArgumentException("Transaction status is required");
        }

        if (!ALLOWED_STATUSES.contains(
                transaction.getTransactionStatus().toUpperCase())) {
            throw new IllegalArgumentException("Invalid transaction status");
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

        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException("Transaction status is required");
        }

        String currentStatus = transaction.getTransactionStatus().toUpperCase();
        String requestedStatus = newStatus.toUpperCase();

        if (!ALLOWED_STATUSES.contains(requestedStatus)) {
            throw new IllegalArgumentException("Invalid transaction status");
        }

        if ("COMPLETED".equals(currentStatus) ||
                "FAILED".equals(currentStatus)) {
            throw new IllegalArgumentException(
                    "Completed or failed transactions cannot be updated");
        }

        transaction.setTransactionStatus(requestedStatus);

        return transaction;
    }
}