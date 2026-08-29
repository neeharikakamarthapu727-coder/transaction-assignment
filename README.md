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

The assignment document mentions that each candidate will receive an assigned variant in the invitation email.

No candidate-specific variant was included in the invitation email I received. I requested clarification from the team and continued with the general requirements so that development would not be delayed.

Until the assigned variant is confirmed, the implementation uses the validation rules documented below.

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
- Candidate-specific currency restrictions were not included in the invitation email.

### Transaction Type
- Transaction Type is stored as part of the transaction.
- Candidate-specific transaction-type restrictions were not included in the invitation email.

### Transaction Status
- Transaction status is stored as part of the transaction.
- The current implementation supports updating the status of an existing transaction.
- More restrictive status-transition rules can be added once the assigned variant or expected status rules are clarified.

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