# AI Usage Disclosure

## AI Tool Used

I used ChatGPT as an AI assistant while working on this assignment.

## What I Used AI For

I used ChatGPT mainly for:

- Understanding the assignment requirements
- Understanding the provided Spring Boot starter project
- Learning how to create Java classes and packages in IntelliJ IDEA
- Understanding REST API concepts
- Structuring the transaction model, repository, service, and controller
- Designing request DTOs
- Adding validation rules
- Introducing enums for transaction type and transaction status
- Converting the repository from an in-memory Java `List` to Spring Data JPA with H2
- Implementing centralized exception handling
- Writing and improving JUnit and MockMvc tests
- Understanding Maven build and test output
- Testing the APIs using Postman
- Preparing and updating project documentation

## Significant AI Suggestions or Generated Content

ChatGPT suggested or helped generate the structure and implementation for:

- `Transaction`
- `CreateTransactionRequest`
- `UpdateTransactionStatusRequest`
- `TransactionType`
- `TransactionStatus`
- `TransactionRepository`
- `TransactionService`
- `TransactionController`
- `GlobalExceptionHandler`
- Custom exception classes
- `TransactionServiceTests`
- `TransactionControllerTests`
- README documentation
- Test result documentation

AI also suggested validation rules, endpoint behaviour, HTTP status codes, status-transition rules, and test scenarios.

## What I Changed, Corrected, or Adjusted

I did not use every suggestion without checking it.

During implementation, I corrected and adjusted several issues, including:

- Creating a package at the wrong level and moving it to the correct package
- Creating a test class in the wrong source folder and moving it to `src/test/java`
- Fixing package names and import placement
- Fixing Postman request setup
- Correcting validation-message expectations in tests
- Updating tests after changing transaction status and transaction type from `String` values to enums
- Updating service tests after converting the repository to a Spring Data JPA interface
- Replacing deprecated `@MockBean` usage with `@MockitoBean`
- Fixing HTTP error handling so invalid requests return appropriate `400`, `404`, or `409` responses instead of generic server errors
- Removing generated `target` files from Git tracking
- Adding `.gitignore`
- Verifying that the project still built successfully after each significant change

I kept the implementation focused on the four operations required by the assignment rather than adding extra unrelated functionality.

## What AI Got Wrong or Needed Correction

Some AI guidance required correction or adjustment during development.

Examples include:

- A test class was initially created in the wrong source location.
- Package placement had to be corrected.
- Import order caused a compilation error and had to be fixed.
- Validation-message expectations in controller tests had to be changed to match the actual API response.
- Some steps had to be adapted to IntelliJ and PowerShell behaviour on my machine.
- After converting fields to enums, older tests and service logic still used strings and had to be updated.
- After converting the repository to Spring Data JPA, service tests could no longer instantiate the repository directly and had to use Mockito.
- I initially asked for clarification regarding the assigned variant mentioned in the assignment document. The team confirmed that the assignment is the same for all candidates and that I should proceed using the requirements and validation rules in the document.

## Final Implementation Improvements

The final project includes:

- Exactly the four required transaction operations
- Separate request DTOs
- Jakarta Bean Validation
- `TransactionType` enum
- `TransactionStatus` enum
- Spring Data JPA
- H2 in-memory database
- Centralized exception handling
- Appropriate HTTP status codes
- Status-transition rules
- Service-layer tests
- Controller tests using MockMvc
- Repository-backed persistence during application runtime
- Clean Git configuration using `.gitignore`
- Updated README and test-results documentation

## How I Verified the Final Result

I verified the application in several ways:

- Confirmed the starter project built successfully
- Ran the Spring Boot application locally
- Tested the REST endpoints manually using Postman
- Verified successful transaction creation
- Verified transaction lookup
- Verified status updates
- Verified customer transaction retrieval
- Verified rejection of invalid transaction amounts
- Verified rejection of duplicate Transaction IDs
- Verified behaviour for missing transactions
- Verified request validation responses
- Verified `201 Created` for successful transaction creation
- Verified `404 Not Found` for a missing transaction
- Verified `409 Conflict` for duplicate transactions
- Verified `409 Conflict` for invalid status transitions
- Verified successful customer transaction retrieval
- Verified an empty list for customers with no transactions
- Ran the complete Maven test suite using:

```text
.\mvnw.cmd clean test
```

Final test result:

```text
Tests run: 15
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

The same final test result is also recorded in:

```text
TEST_RESULTS.txt
```