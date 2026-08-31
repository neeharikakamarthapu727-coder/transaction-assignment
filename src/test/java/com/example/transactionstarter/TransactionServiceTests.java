package com.example.transactionstarter;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionRepository;
import com.example.transactionstarter.transaction.TransactionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void shouldRejectInvalidAmount() {

        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);

        Transaction transaction = new Transaction(
                "TXN101",
                "CUST101",
                new BigDecimal("-100.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createTransaction(transaction)
        );
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

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createTransaction(duplicateTransaction)
        );
    }

    @Test
    void shouldThrowErrorWhenTransactionNotFound() {

        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getTransaction("TXN999")
        );
    }

    @Test
    void shouldRejectInvalidTransactionStatus() {

        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);

        Transaction transaction = new Transaction(
                "TXN103",
                "CUST103",
                new BigDecimal("300.00"),
                "INR",
                "PAYMENT",
                "HELLO"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createTransaction(transaction)
        );
    }

    @Test
    void shouldRejectStatusChangeAfterCompletion() {

        TransactionRepository repository = new TransactionRepository();
        TransactionService service = new TransactionService(repository);

        Transaction transaction = new Transaction(
                "TXN104",
                "CUST104",
                new BigDecimal("700.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        service.createTransaction(transaction);

        service.updateStatus("TXN104", "COMPLETED");

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateStatus("TXN104", "FAILED")
        );
    }
}