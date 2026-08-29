package com.example.transactionstarter.transaction;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    public Optional<Transaction> findById(String transactionId) {
        return transactions.stream()
                .filter(t -> t.getTransactionId().equals(transactionId))
                .findFirst();
    }

    public List<Transaction> findByCustomerId(String customerId) {
        return transactions.stream()
                .filter(t -> t.getCustomerId().equals(customerId))
                .toList();
    }
}
