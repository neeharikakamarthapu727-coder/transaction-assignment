# AI Usage Disclosure

## AI Tool Used

I used ChatGPT as an AI assistant while working on this assignment.

## What I Used AI For

I used ChatGPT mainly for:

- Understanding the assignment requirements
- Understanding the Spring Boot starter project
- Learning how to create Java classes and packages in IntelliJ IDEA
- Understanding REST API concepts
- Structuring the Transaction model, repository, service and controller
- Writing validation logic
- Understanding exception handling
- Writing JUnit test cases
- Testing the APIs using Postman
- Preparing the README documentation

## Significant AI Suggestions or Generated Content

ChatGPT suggested the initial structure for:

- `Transaction`
- `TransactionRepository`
- `TransactionService`
- `TransactionController`
- `GlobalExceptionHandler`
- `TransactionServiceTests`

It also suggested example validation logic and API endpoint design.

## What I Changed or Corrected

I did not copy everything blindly. While implementing the project, I corrected issues such as:

- Creating a package at the wrong level and moving it to the correct package
- Creating a test class in the wrong source folder and moving it to `src/test/java`
- Fixing package names
- Fixing Postman request setup
- Changing error handling so invalid requests return `400 Bad Request` instead of a generic `500 Internal Server Error`
- Checking that all four required tests were actually detected and executed

I also kept the implementation simple instead of adding unnecessary complexity.

## What AI Got Wrong or Needed Correction

During the process, some guidance required correction or adjustment.

Examples include:

- A test class was initially created in the wrong source location
- Package placement had to be corrected
- Some steps had to be adjusted based on IntelliJ and PowerShell behaviour on my machine
- The assignment document mentioned a candidate-specific variant, but no variant was present in my invitation email, so I contacted the team for clarification instead of inventing one

## How I Verified the Final Result

I verified the application in several ways:

- Confirmed the starter project built successfully
- Ran the Spring Boot application locally
- Tested the REST endpoints manually using Postman
- Verified valid transaction creation
- Verified transaction lookup
- Verified status update
- Verified customer transaction retrieval
- Verified rejection of negative transaction amounts
- Verified rejection of duplicate Transaction IDs
- Verified behaviour for a missing transaction
- Ran the automated Maven test suite using:

```text
.\mvnw.cmd clean test
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS

