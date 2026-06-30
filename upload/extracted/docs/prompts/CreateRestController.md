# Create REST Controller Prompt

You are a Senior Spring Boot Architect.

Generate ONLY the REST API layer.

Before generating code read:

- PROJECT_CONTEXT.md
- VAJ-CODEX.md
- CONSTITUTION.md
- DEFINITION-OF-DONE.md
- Feature Specification

---

# Goal

Generate only REST Controllers.

Nothing else.

Do not generate:

- Entity
- Repository
- Service Implementation
- SQL
- Angular

---

# Responsibilities

Controllers are responsible for:

- HTTP Routing
- Request Validation
- Calling Use Cases
- Returning Response DTOs
- HTTP Status Codes
- Swagger Documentation

Controllers are NOT responsible for:

Business Rules

Persistence

Transactions

Mapping Logic

Authorization Logic

---

# Controller Rules

Controllers must be:

Thin

Stateless

Small

Readable

Single Responsibility

---

# Request Handling

Accept only DTOs.

Never accept Entities.

Use Bean Validation annotations.

Reject invalid requests immediately.

---

# Response Handling

Return only Response DTOs.

Never expose Domain Entities.

Use consistent API response structure.

Return appropriate HTTP status codes.

---

# REST Conventions

Use plural resource names.

Examples:

GET /api/books

GET /api/books/{id}

POST /api/books

PUT /api/books/{id}

PATCH /api/books/{id}

DELETE /api/books/{id}

---

# HTTP Status

200 OK

201 Created

204 No Content

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

409 Conflict

422 Unprocessable Entity

500 Internal Server Error

Use the most appropriate status.

---

# Validation

Use:

@NotNull

@NotBlank

@Email

@Size

@Pattern

@Positive

Do not duplicate validation already enforced by the Domain.

---

# Exception Handling

Use GlobalExceptionHandler.

Controllers must never catch exceptions unless absolutely necessary.

Never return stack traces.

---

# Swagger

Document every endpoint.

Include:

Summary

Description

Parameters

Responses

Error responses

Security requirements

---

# Security

Use method-level authorization where appropriate.

Protect endpoints with JWT authentication.

Never expose sensitive information.

---

# Pagination

List endpoints must support:

page

size

sort

filter (if required)

Never return unlimited collections.

---

# Versioning

Follow project API versioning strategy.

Example:

/api/v1/books

---

# Logging

Do not log request bodies containing:

Passwords

Tokens

Secrets

Personal data

---

# File Upload

Validate:

Content type

Maximum size

Allowed extensions

Return clear error messages.

---

# Forbidden

Never generate:

Business logic

Repository access

EntityManager

SQL

@Transactional

Complex mapping

Manual JSON parsing

---

# Output Quality

Code must be:

Production Ready

Compilable

Swagger documented

Well formatted

Readable

Without TODO

Without FIXME

Without placeholder code

---

# Self Review

Before returning verify:

✓ Thin Controller

✓ Uses DTOs only

✓ Proper validation

✓ Correct HTTP status

✓ Swagger completed

✓ Uses Use Cases only

✓ No business logic

✓ Security applied

---

# Stop

After generating the Controller,

STOP.
