# Customer Transaction Processing Service

## 1. Problem Understanding

This project implements a small transaction-processing REST service using Java and Spring Boot.

The service supports the four operations required in the engineering challenge:

1. Create a transaction
2. Get a transaction by Transaction ID
3. Update the status of an existing transaction
4. Get all transactions belonging to a Customer ID

Each transaction contains:

- Transaction ID
- Customer ID
- Amount
- Currency
- Transaction Type
- Transaction Status

---

## 2. Assumptions

## 2. Assumptions

The assignment is the same for all candidates.

The implementation follows the validation rules and requirements described in the assignment document. Where specific business rules were not explicitly fixed, reasonable validation choices were made and documented below.
---

## 3. Validation Rules

The following validation rules are currently implemented:

### Transaction ID
- Transaction ID is required.
- Transaction ID cannot be blank.
- Each Transaction ID must be unique.
- A duplicate Transaction ID is rejected.

### Customer ID
- Customer ID is required.
- Customer ID cannot be blank.

### Amount
- Amount is required.
- Amount must be greater than zero.

### Currency
- Currency is stored as part of the transaction.
- No additional currency restriction was specified in the assignment requirements.

### Transaction Type
- Transaction Type is stored as part of the transaction.
- No additional transaction-type restriction was specified in the assignment requirements.

### Transaction Status
- Transaction Status is stored as part of the transaction.
- The current implementation allows the status of an existing transaction to be updated.
- More restrictive status-transition rules could be added as a future improvement.

---

## 4. API Endpoints

### Create Transaction

**Method:** POST

**Endpoint:**

`/api/transactions`

Example request:

```json
{
  "transactionId": "TXN001",
  "customerId": "CUST001",
  "amount": 1000.00,
  "currency": "INR",
  "transactionType": "PAYMENT",
  "transactionStatus": "PENDING"
}