package com.example.transactionstarter;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;
import com.example.transactionstarter.transaction.TransactionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceTests {

    @Test
    void shouldCreateTransactionSuccessfully() {

        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);

        Transaction transaction = new Transaction(
                "TXN100",
                "CUST100",
                new BigDecimal("500.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        Transaction result = service.createTransaction(transaction);

        assertEquals("TXN100", result.getTransactionId());
        assertEquals("CUST100", result.getCustomerId());
        assertEquals(new BigDecimal("500.00"), result.getAmount());
    }
    @Test
    void shouldRejectDuplicateTransactionId() {

        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);

        Transaction firstTransaction = new Transaction(
                "TXN102",
                "CUST102",
                new BigDecimal("500.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        Transaction duplicateTransaction = new Transaction(
                "TXN102",
                "CUST103",
                new BigDecimal("800.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        service.createTransaction(firstTransaction);

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.createTransaction(duplicateTransaction)
        );
    }
    @Test
    void shouldThrowErrorWhenTransactionNotFound() {

        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.getTransaction("TXN999")
        );
    }
}
