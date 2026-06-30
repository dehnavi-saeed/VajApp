# VAJ CONSTITUTION

Version: 1.0

This document defines the immutable engineering rules of the Vaj project.

Every AI assistant (Codex, ChatGPT, Claude, GLM) MUST follow these rules.

These rules have higher priority than implementation preferences.

---

# Rule 1

Business correctness is more important than generating code quickly.

Never sacrifice correctness for speed.

---

# Rule 2

Architecture consistency is mandatory.

Every new code must match the existing architecture.

Never introduce another architecture style.

---

# Rule 3

DDD is mandatory.

Business logic belongs inside the Domain.

Never move business rules into Controllers.

Never move business rules into Infrastructure.

---

# Rule 4

Controllers are thin.

Controllers only:

- Validate request
- Call Application Service
- Return Response

Nothing else.

---

# Rule 5

Services are use cases.

Application Services orchestrate the domain.

They never contain persistence logic.

---

# Rule 6

Repositories are interfaces.

Infrastructure implements repositories.

Never access JPA directly from Application.

---

# Rule 7

Entities protect themselves.

Invalid Entity state must never exist.

Always validate invariants.

---

# Rule 8

Value Objects are immutable.

Never expose mutable Value Objects.

---

# Rule 9

DTOs are required.

Never expose Entities outside the Application layer.

---

# Rule 10

MapStruct is mandatory.

Never write manual mapping unless absolutely necessary.

---

# Rule 11

Dependency Injection

Constructor Injection only.

Never use Field Injection.

---

# Rule 12

SOLID principles are mandatory.

Every generated class must respect SOLID.

---

# Rule 13

Avoid large classes.

Preferred limits:

Class

< 300 lines

Method

< 40 lines

Cyclomatic Complexity

As low as possible.

---

# Rule 14

Never duplicate business logic.

Extract reusable logic.

---

# Rule 15

Naming must be explicit.

Good

BookService

CreateBookUseCase

EpisodeReader

Bad

Utils

Manager

Helper

ServiceImpl

---

# Rule 16

Never create Generic Repository.

Repositories represent Aggregates.

---

# Rule 17

No static business logic.

Business rules belong inside Domain objects.

---

# Rule 18

Every Feature is isolated.

Each feature owns:

Controller

Application

Domain

Infrastructure

DTO

Mapper

Tests

Configuration

---

# Rule 19

Every API must:

Validate input

Return consistent responses

Handle exceptions

Return proper HTTP status

---

# Rule 20

Never swallow exceptions.

Always return meaningful errors.

---

# Rule 21

Logging

Use structured logging.

Never log passwords.

Never log tokens.

Never log secrets.

---

# Rule 22

Security First.

Every endpoint must be evaluated for:

Authentication

Authorization

Input Validation

Injection

Rate Limiting

---

# Rule 23

Database

Flyway only.

No automatic schema generation in production.

---

# Rule 24

Soft Delete

Use Soft Delete only where business requires.

Avoid unnecessary hidden records.

---

# Rule 25

Transactions

Transactions belong to Application layer.

Never inside Controller.

---

# Rule 26

Events

Business events are preferred over direct coupling.

---

# Rule 27

Tests are mandatory.

Every Feature should contain:

Unit Tests

Integration Tests

Repository Tests

---

# Rule 28

Swagger documentation is required.

Every endpoint must be documented.

---

# Rule 29

Code Review

Generated code must be reviewed against:

Architecture

Performance

Security

Maintainability

Readability

---

# Rule 30

AI must stop after requested scope.

Never continue implementing unrelated modules.

Implement only the requested task.

---

# Rule 31

Never generate placeholder code.

Forbidden:

TODO

FIXME

Dummy implementation

Fake repository

Temporary logic

---

# Rule 32

Backward compatibility matters.

Avoid breaking public APIs.

---

# Rule 33

Performance

Avoid N+1 queries.

Avoid unnecessary allocations.

Avoid loading unnecessary entities.

---

# Rule 34

Readability beats cleverness.

Simple code is preferred.

---

# Rule 35

One Responsibility per class.

If a class has multiple responsibilities,
split it.

---

# Rule 36

No magic values.

Use Constants

Enums

Configuration

---

# Rule 37

Documentation

Every public class should explain:

Purpose

Responsibilities

Usage

---

# Rule 38

AI Output Quality

Generated code must be:

Production Ready

Compilable

Consistent

Reviewed

Without warnings

Without dead code

Without duplicate logic

---

# Rule 39

If requirements are ambiguous:

Stop.

Ask for clarification.

Never invent business rules.

---

# Rule 40

Quality over quantity.

Generating fewer but cleaner files is always preferred.
