# VAJ Backend Development Rules

Version: 3.0

---

# Purpose

This document defines backend implementation rules.

Business correctness is always more important than framework features.

Spring Boot is a tool.

The business domain is the product.

---

# Backend Philosophy

Backend code must be:

- Predictable
- Testable
- Stateless
- Secure
- Modular
- Maintainable

Avoid unnecessary complexity.

---

# Application Layers

Presentation

↓

Application

↓

Domain

↓

Persistence

↓

Infrastructure

Business rules belong to the Domain.

---

# Controllers

Controllers should only:

- Receive HTTP requests
- Validate DTOs
- Call Services
- Return Responses

Controllers never:

- Execute business logic
- Access repositories
- Execute SQL
- Call EntityManager
- Build Entities manually

Controllers should remain small.

Target size:

<150 lines.

---

# Services

Services coordinate business operations.

Services own transactions.

Services enforce business rules.

Services publish Domain Events.

Services never:

- Parse HTTP requests
- Build HTML
- Execute raw SQL
- Contain UI logic

---

# Repositories

Repositories only access persistence.

Repositories never:

- Validate business rules
- Access SecurityContext
- Call remote services
- Publish events

Repository methods should express intent.

Good

findActiveBooks()

existsByIsbn()

findCompletedReadingSessions()

Bad

execute()

query()

loadData()

---

# Entities

Entities model business concepts.

Entities protect invariants.

Entities never:

- Access Spring
- Access HTTP
- Execute SQL
- Send Notifications
- Call APIs

---

# DTO

DTOs transport data.

DTOs contain no business logic.

Separate:

Request

Response

Search

Projection

Never reuse Entity as DTO.

---

# Mapper

Use MapStruct.

Mappers perform object transformation only.

No validation.

No repositories.

No services.

---

# Validation

Structural validation

↓

DTO

Business validation

↓

Service

Persistence validation

↓

Database

Never duplicate validations.

---

# Transactions

Transactions belong only to Services.

Keep transactions short.

Never call external APIs inside transactions.

Never keep transactions open while waiting for users.

---

# Domain Events

Services publish Domain Events.

Consumers remain independent.

Avoid synchronous coupling.

---

# Security

Authentication

↓

Authorization

↓

Business Validation

↓

Execution

Never skip ownership validation.

---

# Exception Handling

One GlobalExceptionHandler.

Consistent error model.

Meaningful business exceptions.

Never expose stack traces.

---

# Logging

Use structured logging.

Never log:

Passwords

Tokens

Secrets

Sensitive personal data

Always log:

Business identifiers

RequestId

Execution failures

---

# Performance

Prefer pagination.

Prefer projections.

Review SQL mentally.

Avoid N+1.

Never load unnecessary object graphs.

---

# AI Rules

When generating backend code:

Follow architecture.

Follow dependency rules.

Protect business invariants.

Prefer readability.

Prefer explicit code.

Generate production-ready implementations only.
