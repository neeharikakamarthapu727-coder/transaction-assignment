package com.example.transactionstarter;

import com.example.transactionstarter.transaction.DuplicateTransactionException;
import com.example.transactionstarter.transaction.InvalidStatusTransitionException;
import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionNotFoundException;
import com.example.transactionstarter.transaction.TransactionRepository;
import com.example.transactionstarter.transaction.TransactionService;
import com.example.transactionstarter.transaction.TransactionStatus;
import com.example.transactionstarter.transaction.TransactionType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTests {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    @Test
    void shouldCreateTransactionSuccessfully() {

        Transaction transaction = new Transaction(
                "TXN100",
                "CUST100",
                new BigDecimal("500.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        when(repository.findById("TXN100"))
                .thenReturn(Optional.empty());

        Transaction result = service.createTransaction(transaction);

        assertEquals("TXN100", result.getTransactionId());
        assertEquals("CUST100", result.getCustomerId());
        assertEquals(TransactionType.PAYMENT, result.getTransactionType());
        assertEquals(TransactionStatus.PENDING, result.getTransactionStatus());

        verify(repository).save(transaction);
    }

    @Test
    void shouldRejectInvalidAmount() {

        Transaction transaction = new Transaction(
                "TXN101",
                "CUST101",
                new BigDecimal("-100.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createTransaction(transaction)
        );
    }

    @Test
    void shouldRejectDuplicateTransactionId() {

        Transaction firstTransaction = new Transaction(
                "TXN102",
                "CUST102",
                new BigDecimal("500.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        Transaction duplicateTransaction = new Transaction(
                "TXN102",
                "CUST103",
                new BigDecimal("800.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        when(repository.findById("TXN102"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(firstTransaction));

        service.createTransaction(firstTransaction);

        assertThrows(
                DuplicateTransactionException.class,
                () -> service.createTransaction(duplicateTransaction)
        );

        verify(repository, times(1)).save(firstTransaction);
    }

    @Test
    void shouldThrowErrorWhenTransactionNotFound() {

        when(repository.findById("TXN999"))
                .thenReturn(Optional.empty());

        assertThrows(
                TransactionNotFoundException.class,
                () -> service.getTransaction("TXN999")
        );
    }

    @Test
    void shouldRejectMissingTransactionStatus() {

        Transaction transaction = new Transaction(
                "TXN103",
                "CUST103",
                new BigDecimal("300.00"),
                "INR",
                TransactionType.PAYMENT,
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createTransaction(transaction)
        );
    }

    @Test
    void shouldRejectStatusChangeAfterCompletion() {

        Transaction transaction = new Transaction(
                "TXN104",
                "CUST104",
                new BigDecimal("700.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        when(repository.findById("TXN104"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(transaction))
                .thenReturn(Optional.of(transaction));

        service.createTransaction(transaction);

        service.updateStatus(
                "TXN104",
                TransactionStatus.COMPLETED
        );

        assertThrows(
                InvalidStatusTransitionException.class,
                () -> service.updateStatus(
                        "TXN104",
                        TransactionStatus.FAILED
                )
        );
    }
}