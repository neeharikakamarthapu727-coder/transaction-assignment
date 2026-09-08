package com.example.transactionstarter;

import com.example.transactionstarter.transaction.DuplicateTransactionException;
import com.example.transactionstarter.transaction.GlobalExceptionHandler;
import com.example.transactionstarter.transaction.InvalidStatusTransitionException;
import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionController;
import com.example.transactionstarter.transaction.TransactionNotFoundException;
import com.example.transactionstarter.transaction.TransactionService;
import com.example.transactionstarter.transaction.TransactionStatus;
import com.example.transactionstarter.transaction.TransactionType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import(GlobalExceptionHandler.class)
class TransactionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService service;

    @Test
    void shouldReturnAllValidationErrorsForInvalidTransaction() throws Exception {

        String invalidRequest = """
                {
                  "transactionId": "",
                  "customerId": "",
                  "amount": -10,
                  "currency": "US",
                  "transactionType": "",
                  "transactionStatus": ""
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Validation failed"))
                .andExpect(jsonPath("$.fields.transactionId")
                        .value("Transaction ID is required"))
                .andExpect(jsonPath("$.fields.customerId")
                        .value("Customer ID is required"))
                .andExpect(jsonPath("$.fields.amount")
                        .value("Amount must be greater than zero"))
                .andExpect(jsonPath("$.fields.currency")
                        .value("Currency must be a 3-letter uppercase code"))
                .andExpect(jsonPath("$.fields.transactionType")
                        .value("Transaction type must be PAYMENT, REFUND, or TRANSFER"))
                .andExpect(jsonPath("$.fields.transactionStatus")
                        .value("Transaction status must be PENDING, COMPLETED, or FAILED"));
    }

    @Test
    void shouldReturn404WhenTransactionNotFound() throws Exception {

        when(service.getTransaction("TXN999"))
                .thenThrow(
                        new TransactionNotFoundException(
                                "Transaction not found"
                        )
                );

        mockMvc.perform(get("/api/transactions/TXN999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("Transaction not found"));
    }

    @Test
    void shouldReturnTransactionsForCustomer() throws Exception {

        Transaction firstTransaction = new Transaction(
                "TXN201",
                "CUST500",
                new BigDecimal("500.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        Transaction secondTransaction = new Transaction(
                "TXN202",
                "CUST500",
                new BigDecimal("250.00"),
                "INR",
                TransactionType.REFUND,
                TransactionStatus.COMPLETED
        );

        when(service.getCustomerTransactions("CUST500"))
                .thenReturn(List.of(
                        firstTransaction,
                        secondTransaction
                ));

        mockMvc.perform(get("/api/transactions/customer/CUST500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].transactionId").value("TXN201"))
                .andExpect(jsonPath("$[0].customerId").value("CUST500"))
                .andExpect(jsonPath("$[0].transactionType").value("PAYMENT"))
                .andExpect(jsonPath("$[0].transactionStatus").value("PENDING"))
                .andExpect(jsonPath("$[1].transactionId").value("TXN202"))
                .andExpect(jsonPath("$[1].customerId").value("CUST500"))
                .andExpect(jsonPath("$[1].transactionType").value("REFUND"))
                .andExpect(jsonPath("$[1].transactionStatus").value("COMPLETED"));
    }

    @Test
    void shouldCreateTransactionAndReturn201() throws Exception {

        Transaction createdTransaction = new Transaction(
                "TXN300",
                "CUST300",
                new BigDecimal("1000.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        when(service.createTransaction(any(Transaction.class)))
                .thenReturn(createdTransaction);

        String requestBody = """
                {
                  "transactionId": "TXN300",
                  "customerId": "CUST300",
                  "amount": 1000.00,
                  "currency": "INR",
                  "transactionType": "PAYMENT",
                  "transactionStatus": "PENDING"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("TXN300"))
                .andExpect(jsonPath("$.customerId").value("CUST300"))
                .andExpect(jsonPath("$.amount").value(1000.00))
                .andExpect(jsonPath("$.currency").value("INR"))
                .andExpect(jsonPath("$.transactionType").value("PAYMENT"))
                .andExpect(jsonPath("$.transactionStatus").value("PENDING"));
    }

    @Test
    void shouldUpdateTransactionStatusSuccessfully() throws Exception {

        Transaction updatedTransaction = new Transaction(
                "TXN400",
                "CUST400",
                new BigDecimal("750.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.COMPLETED
        );

        when(service.updateStatus(
                "TXN400",
                TransactionStatus.COMPLETED
        )).thenReturn(updatedTransaction);

        String requestBody = """
                {
                  "status": "COMPLETED"
                }
                """;

        mockMvc.perform(put("/api/transactions/TXN400/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("TXN400"))
                .andExpect(jsonPath("$.customerId").value("CUST400"))
                .andExpect(jsonPath("$.transactionStatus").value("COMPLETED"));
    }

    @Test
    void shouldReturn409ForDuplicateTransaction() throws Exception {

        when(service.createTransaction(any(Transaction.class)))
                .thenThrow(
                        new DuplicateTransactionException(
                                "Transaction ID already exists"
                        )
                );

        String requestBody = """
                {
                  "transactionId": "TXN500",
                  "customerId": "CUST500",
                  "amount": 500.00,
                  "currency": "INR",
                  "transactionType": "PAYMENT",
                  "transactionStatus": "PENDING"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error")
                        .value("Transaction ID already exists"));
    }

    @Test
    void shouldReturn409ForInvalidStatusTransition() throws Exception {

        when(service.updateStatus(
                "TXN600",
                TransactionStatus.FAILED
        )).thenThrow(
                new InvalidStatusTransitionException(
                        "Completed or failed transactions cannot be updated"
                )
        );

        String requestBody = """
                {
                  "status": "FAILED"
                }
                """;

        mockMvc.perform(put("/api/transactions/TXN600/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error")
                        .value("Completed or failed transactions cannot be updated"));
    }

    @Test
    void shouldReturnEmptyListWhenCustomerHasNoTransactions() throws Exception {

        when(service.getCustomerTransactions("CUST999"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/transactions/customer/CUST999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}