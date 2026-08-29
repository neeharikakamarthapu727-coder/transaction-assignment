package com.example.transactionstarter.transaction;

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
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return service.createTransaction(transaction);
    }

    @GetMapping("/{transactionId}")
    public Transaction getTransaction(@PathVariable String transactionId) {
        return service.getTransaction(transactionId);
    }

    @PutMapping("/{transactionId}/status")
    public Transaction updateStatus(@PathVariable String transactionId,
                                    @RequestParam String status) {
        return service.updateStatus(transactionId, status);
    }

    @GetMapping("/customer/{customerId}")
    public List<Transaction> getCustomerTransactions(@PathVariable String customerId) {
        return service.getCustomerTransactions(customerId);
    }
}
