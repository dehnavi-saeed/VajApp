# VAJ Architecture

Version: 3.0

---

# Architecture Style

VAJ follows a Feature-Based Modular Monolith architecture.

The system is designed to be modular from the beginning while remaining deployable as a single application.

Microservices are NOT a current goal.

The architecture must allow future extraction of modules if business requirements change.

---

# Guiding Principles

The architecture prioritizes:

- Simplicity
- Maintainability
- Modularity
- Scalability
- Testability
- Readability

Avoid architectural complexity unless there is a measurable benefit.

---

# Architectural Pattern

Presentation Layer

↓

Application Layer

↓

Domain Layer

↓

Infrastructure Layer

Dependencies always flow inward.

Business rules never depend on infrastructure.

---

# Package Organization

The project is organized by business features.

Example:

app.vaj

    auth

    user

    library

    book

    reading

    highlight

    note

    goal

    statistics

    notification

    common

Never organize the project only by technical layers.

---

# Feature Structure

Every feature follows the same internal structure.

controller

service

repository

entity

dto

mapper

validation

exception

config

Each feature owns its business logic.

---

# Module Independence

Features should communicate only through public services.

Never access another feature's repository directly.

Avoid circular dependencies.

---

# Domain Ownership

Every business capability belongs to exactly one feature.

Business logic must never be duplicated across features.

---

# Shared Code

The common module contains only reusable infrastructure.

Examples:

security

configuration

audit

logging

response

exception

utility

Never move business logic into common.

---

# Dependency Rules

Controller

↓

Service

↓

Repository

↓

Database

Controllers never call repositories.

Repositories never call controllers.

Entities never depend on Spring.

---

# Transactions

Transaction boundaries belong only in the Service layer.

Controllers must never manage transactions.

Repositories should never define business transactions.

---

# Mapping

External DTO

↓

MapStruct

↓

Entity

↓

Repository

Never expose entities directly through REST.

---

# Validation

Request validation

↓

DTO

Business validation

↓

Service

Persistence validation

↓

Database

Every validation has exactly one responsibility.

---

# Security

Authentication belongs to the Auth feature.

Authorization belongs to the business feature.

Never mix authentication with business rules.

---

# Configuration

Global configuration:

app.vaj.config

Feature configuration:

app.vaj.<feature>.config

Never duplicate configuration.

---

# Events

The architecture should be event-ready.

Current implementation is synchronous.

Future asynchronous processing should not require major refactoring.

---

# Scalability

The architecture should support:

Thousands of users

Millions of books

Millions of notes

Millions of highlights

without architectural redesign.

---

# AI Rules

When generating code:

- Follow the existing feature structure.
- Never introduce new architectural styles.
- Never bypass the Service layer.
- Never place business logic in Controllers.
- Never duplicate business rules.
- Prefer consistency over creativity.
