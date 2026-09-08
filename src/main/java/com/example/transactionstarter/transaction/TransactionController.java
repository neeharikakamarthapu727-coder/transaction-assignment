package com.example.transactionstarter.transaction;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request) {

        Transaction transaction = new Transaction(
                request.getTransactionId(),
                request.getCustomerId(),
                request.getAmount(),
                request.getCurrency(),
                TransactionType.valueOf(request.getTransactionType()),
                TransactionStatus.valueOf(request.getTransactionStatus())
        );

        Transaction createdTransaction =
                service.createTransaction(transaction);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTransaction);
    }

    @GetMapping("/{transactionId}")
    public Transaction getTransaction(
            @PathVariable String transactionId) {

        return service.getTransaction(transactionId);
    }

    @PutMapping("/{transactionId}/status")
    public Transaction updateStatus(
            @PathVariable String transactionId,
            @Valid @RequestBody UpdateTransactionStatusRequest request) {

        TransactionStatus newStatus =
                TransactionStatus.valueOf(request.getStatus());

        return service.updateStatus(
                transactionId,
                newStatus
        );
    }

    @GetMapping("/customer/{customerId}")
    public List<Transaction> getCustomerTransactions(
            @PathVariable String customerId) {

        return service.getCustomerTransactions(customerId);
    }
}