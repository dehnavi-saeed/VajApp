# PROJECT_CONTEXT.md

> Global Project Context for AI Assistants (Codex, ChatGPT, Claude, GLM)

---

# Project

Name: Vaj

Description:

Vaj is a modern online book reading platform where books are divided into episodes.
Users can read free episodes and unlock premium episodes by purchasing a subscription or individual episodes.

The platform is designed with an AI-First development workflow.

---

# Vision

Build a clean, scalable, maintainable platform that can grow from MVP to a large production system.

The codebase must always prioritize:

- Clean Architecture
- DDD
- SOLID
- Readability
- Testability
- Performance

---

# Architecture

Backend

- Java 21
- Spring Boot 3.x
- Maven

Architecture Style

- Domain Driven Design (DDD)
- Feature Based Modules
- Clean Architecture
- Hexagonal Architecture principles
- CQRS where appropriate

Frontend

- Angular
- Tailwind CSS
- TypeScript

Database

- SQL Server

ORM

- Spring Data JPA
- Hibernate

Mapping

- MapStruct

Authentication

- JWT
- Refresh Token
- Role Based Authorization

Storage

- Local Storage (Development)
- S3 Compatible Storage (Future)

Cache

- Redis (Future)

Search

- PostgreSQL Full Text Search initially
- Elasticsearch (Future)

Messaging

- Spring Events
- Kafka (Future)

---

# Business Domain

The platform contains:

- Users
- Books
- Categories
- Episodes
- Reading Progress
- Purchases
- Subscriptions
- Payments
- Notifications

---

# Core Business Rules

Books contain multiple Episodes.

Episodes have an Order.

Episodes may be Free or Premium.

Premium Episodes require:

- Active Subscription
  OR
- Episode Purchase

Reading Progress is stored per user.

Deleting Books must never remove reading history.

Subscriptions have expiration dates.

Payments are immutable.

Transactions are auditable.

---

# Coding Standards

Always:

- Constructor Injection
- Lombok
- DTOs
- Validation
- Global Exception Handling
- Pagination
- API Versioning
- Swagger Documentation
- Integration Tests

Never:

- Field Injection
- Business Logic inside Controller
- Expose Entity directly
- Generic Repository
- God Services
- Static Utility Classes for Business Logic

---

# Naming Rules

Packages

app.vaj.feature

Classes

BookService
BookController
BookRepository

DTO

CreateBookRequest
BookResponse

Mapper

BookMapper

Events

BookCreatedEvent

Exceptions

BookNotFoundException

---

# API Rules

RESTful

Use plural names.

Example

/api/books

/api/books/{id}

/api/categories

/api/profile

/api/subscriptions

Return standard Response DTO.

Never expose Entity.

---

# Security Rules

JWT Authentication

Refresh Token Rotation

Password Hashing

Spring Security

Method Authorization

HTTPS Only

Rate Limiting (Future)

Audit Log

---

# Database Rules

Use Flyway.

No automatic schema generation in Production.

Soft Delete where applicable.

Every table contains:

- CreatedAt
- UpdatedAt
- CreatedBy
- UpdatedBy

UUID may be used for public identifiers.

---

# Testing Rules

JUnit 5

Mockito

Testcontainers

Integration Tests

Repository Tests

Controller Tests

---

# AI Workflow

Before implementing any feature:

1. Read PROJECT_CONTEXT.md

2. Read VAJ-CODEX.md

3. Read Feature Specification

4. Read BuildOrder.md

Only then generate code.

---

# Output Quality

Generated code must be:

Production Ready

Clean

Modular

Maintainable

Well documented

Testable

No placeholders

No TODO comments

No pseudo code

No duplicated logic

---

# Goal

Every generated source code should be ready to merge into the main branch without architectural changes.
