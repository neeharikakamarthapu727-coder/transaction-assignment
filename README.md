# Customer Transaction Processing Service

## 1. Problem Understanding

This project implements a small transaction-processing REST API using Java and Spring Boot.

The service supports exactly the four operations required in the engineering challenge:

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

- The same assignment requirements apply to all candidates.
- Reasonable validation and status-transition rules were chosen where the assignment did not prescribe exact business rules.
- Currency values are represented using 3-letter uppercase codes such as `INR`, `USD`, or `EUR`.
- Supported transaction types are `PAYMENT`, `REFUND`, and `TRANSFER`.
- Supported transaction statuses are `PENDING`, `COMPLETED`, and `FAILED`.

---

## 3. Validation Rules

### Transaction ID

- Required.
- Cannot be blank.
- Maximum length: 64 characters.
- Must be unique.
- Duplicate Transaction IDs are rejected.

### Customer ID

- Required.
- Cannot be blank.
- Maximum length: 64 characters.

### Amount

- Required.
- Must be greater than zero.
- Supports up to 2 decimal places.

### Currency

- Required.
- Must contain exactly 3 uppercase letters.
- Examples: `INR`, `USD`, `EUR`.

### Transaction Type

Allowed values:

- `PAYMENT`
- `REFUND`
- `TRANSFER`

The application uses a `TransactionType` enum internally.

### Transaction Status

Allowed values:

- `PENDING`
- `COMPLETED`
- `FAILED`

The application uses a `TransactionStatus` enum internally.

Status transition rules:

- A `PENDING` transaction may be changed to `COMPLETED` or `FAILED`.
- Once a transaction becomes `COMPLETED` or `FAILED`, its status cannot be changed again.

---

## 4. API Endpoints

### A. Create Transaction

**Method:** `POST`

**Endpoint:**

```text
/api/transactions
```

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
```

Successful response:

```text
201 Created
```

---

### B. Get Transaction by ID

**Method:** `GET`

**Endpoint:**

```text
/api/transactions/{transactionId}
```

Example:

```text
/api/transactions/TXN001
```

Successful response:

```text
200 OK
```

If the transaction does not exist:

```text
404 Not Found
```

---

### C. Update Transaction Status

**Method:** `PUT`

**Endpoint:**

```text
/api/transactions/{transactionId}/status
```

Example request:

```json
{
  "status": "COMPLETED"
}
```

Successful response:

```text
200 OK
```

An invalid status transition returns:

```text
409 Conflict
```

---

### D. Get Transactions by Customer ID

**Method:** `GET`

**Endpoint:**

```text
/api/transactions/customer/{customerId}
```

Example:

```text
/api/transactions/customer/CUST001
```

Successful response:

```text
200 OK
```

If the customer has no transactions, an empty JSON array is returned.

---

## 5. Error Handling

The application uses centralized exception handling through `GlobalExceptionHandler`.

HTTP responses include:

| Situation | HTTP Status |
|---|---|
| Successful transaction creation | `201 Created` |
| Successful GET or status update | `200 OK` |
| Invalid request or validation failure | `400 Bad Request` |
| Transaction not found | `404 Not Found` |
| Duplicate Transaction ID | `409 Conflict` |
| Invalid status transition | `409 Conflict` |

Example validation response:

```json
{
  "error": "Validation failed",
  "fields": {
    "transactionId": "Transaction ID is required",
    "amount": "Amount must be greater than zero",
    "currency": "Currency must be a 3-letter uppercase code"
  }
}
```

---

## 6. Persistence

The application uses:

- Spring Data JPA
- H2 Database

`TransactionRepository` extends `JpaRepository`.

Transaction type and transaction status are persisted as readable string values using enums.

The current H2 database is configured as an in-memory database, so stored data is cleared when the application is restarted.

---

## 7. Project Structure

```text
src/main/java/com/example/transactionstarter
│
├── TransactionStarterApplication.java
│
└── transaction
    ├── CreateTransactionRequest.java
    ├── UpdateTransactionStatusRequest.java
    ├── Transaction.java
    ├── TransactionType.java
    ├── TransactionStatus.java
    ├── TransactionRepository.java
    ├── TransactionService.java
    ├── TransactionController.java
    ├── GlobalExceptionHandler.java
    ├── TransactionNotFoundException.java
    ├── DuplicateTransactionException.java
    └── InvalidStatusTransitionException.java
```

---

## 8. Testing

The project includes automated service and controller tests.

Tests cover:

- Successful transaction creation
- Invalid transaction amount
- Duplicate Transaction ID
- Transaction not found
- Missing transaction status
- Status-transition rules
- Request validation
- Field-level validation messages
- `201 Created` response
- `404 Not Found` response
- `409 Conflict` for duplicate transactions
- `409 Conflict` for invalid status transitions
- Successful status update
- Customer transaction retrieval
- Empty customer transaction list
- Spring Boot application context startup

Latest test result:

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

The same result is also recorded in:

```text
TEST_RESULTS.txt
```

---

## 9. Running the Application

### Requirements

- Java 17
- Maven Wrapper included in the project

### Run tests on Windows

```powershell
.\mvnw.cmd clean test
```

### Run the application on Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Run tests on Linux/macOS

```bash
./mvnw clean test
```

### Run the application on Linux/macOS

```bash
./mvnw spring-boot:run
```

---

## 10. Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Jakarta Bean Validation
- H2 Database
- Maven
- JUnit 5
- Mockito
- MockMvc

---

## 11. Design Decisions

### DTOs

Separate request DTOs are used for:

- Creating transactions
- Updating transaction status

This keeps API input validation separate from the persistence model.

### Enums

Enums are used for:

- Transaction Type
- Transaction Status

This prevents unsupported values from being represented inside the domain model.

### Status Rules

Completed and failed transactions are treated as terminal transactions and cannot be modified again.

### Repository

Spring Data JPA is used instead of manual `List`-based storage to provide a clearer persistence layer.

---

## 12. Current Limitations

- H2 is configured as an in-memory database, so data is not retained after application restart.
- Authentication and authorization are outside the scope of this assignment.
- Pagination is not implemented for customer transaction retrieval.

---

## 13. Possible Future Improvements

With additional time, the following improvements could be considered:

- Use a persistent production database such as PostgreSQL.
- Add pagination for customer transaction history.
- Add structured logging.
- Add API documentation using OpenAPI/Swagger.
- Add database integration tests.
- Add timestamps such as transaction creation and update time.
- Introduce response DTOs to further separate the API layer from persistence entities.

---

## 14. AI Usage

AI assistance was used during development and is documented separately in:

```text
AI_USAGE_DISCLOSURE.md
```

The final implementation was reviewed and verified by running the complete automated test suite.