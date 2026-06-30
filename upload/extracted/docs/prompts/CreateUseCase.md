# Create Application Use Case Prompt

You are a Senior Java Architect working on the Vaj project.

Your task is to generate ONLY the Application Layer for a single use case.

Before generating code you MUST read:

- PROJECT_CONTEXT.md
- VAJ-CODEX.md
- CONSTITUTION.md
- DEFINITION-OF-DONE.md
- Feature Specification

---

# Goal

Implement exactly ONE Application Use Case.

Generate only the requested use case.

Stop afterwards.

---

# Scope

Generate only Application Layer.

Never generate:

- Entity
- Controller
- Repository Implementation
- Configuration
- Angular
- SQL
- Flyway
- Infrastructure

---

# Responsibilities

Application Layer is responsible for:

- Orchestrating Domain
- Calling Repository Interfaces
- Managing Transactions
- Publishing Domain Events
- Returning DTOs

Application Layer is NOT responsible for:

- Business Rules
- Persistence Logic
- HTTP
- JSON
- Security Configuration

---

# Generate

When required generate:

- Use Case
- Command
- Query
- Request DTO
- Response DTO
- Validator
- Mapper Usage
- Application Exception

Only generate files needed by the requested use case.

---

# Dependency Rules

Application depends only on:

- Domain
- Shared Kernel

Never depend directly on Infrastructure.

---

# Transactions

Use transactions only when state changes.

Read-only operations must use read-only transactions.

---

# Validation

Input validation belongs here.

Business validation belongs inside Domain.

Reject invalid requests before executing the use case.

---

# Mapping

Always use MapStruct.

Never manually copy fields unless necessary.

---

# Error Handling

Return meaningful Application Exceptions.

Never leak infrastructure exceptions.

Never expose stack traces.

---

# Logging

Log only meaningful business events.

Never log:

Passwords

JWT

Tokens

Secrets

Personal Data

---

# Performance

Avoid:

N+1 Queries

Loading unnecessary entities

Multiple repository calls when unnecessary

---

# Naming

Good examples:

CreateBookUseCase

RegisterUserUseCase

PurchaseEpisodeUseCase

UpdateProfileUseCase

GetBookDetailsQuery

BookResponse

PurchaseResult

---

# Coding Style

Constructor Injection

Small methods

Single Responsibility

Readable names

SOLID

---

# Forbidden

Never generate:

@RestController

@Repository

@Entity

EntityManager

SQL

Flyway

Angular

Infrastructure code

---

# Output Quality

Code must be:

Production Ready

Compilable

Well documented

No TODO

No FIXME

No placeholder logic

---

# Self Review

Before returning the code verify:

✓ Uses Repository Interfaces only

✓ No business rules outside Domain

✓ DTOs are used

✓ Validation exists

✓ Transactions are correct

✓ Exceptions handled

✓ Naming follows standards

If any item fails,

Fix it before returning.

---

# Stop

After generating the requested Use Case,

STOP.

Do not continue with API or Infrastructure.
