# VAJ-BACKEND-CODEX.md

Version: 2.0

Status: Official

---

# Purpose

This document defines every backend development rule for VAJ.

Every AI assistant generating Java code MUST follow these rules.

If generated code violates these rules,

the generated code is considered incorrect.

---

# Backend Technology Stack

Language

Java 21 LTS

Framework

Spring Boot 3.x

Architecture

Spring Modulith

Build Tool

Maven

Database

SQL Server 2022

Migration

Flyway

ORM

Hibernate 6

Spring Data JPA

Mapper

MapStruct

Validation

Jakarta Validation

Security

Spring Security

JWT

Refresh Token

Documentation

OpenAPI

Testing

JUnit 5

Mockito

Testcontainers

ArchUnit

---

# Java Version

Always target Java 21.

Never use preview features.

Prefer stable language features.

Use:

Records

Sealed Classes

Pattern Matching

Virtual Threads only after explicit approval.

---

# Architectural Style

Backend follows:

DDD

Clean Architecture

CQRS-Friendly

Modular Monolith

Feature-Based Packaging

No layered monolith.

---

# Package Structure

app.vaj

auth

user

library

catalog

reading

knowledge

analytics

platform

common

Every Feature owns:

api

application

domain

infra

No custom layouts.

---

# Dependency Rule

Allowed

API

↓

Application

↓

Domain

Infrastructure

↓

Domain

Forbidden

Domain → Spring

Domain → Hibernate

Domain → JPA

API → Repository

Controller → Entity

Entity → Repository

---

# Domain Rules

The Domain is the most important layer.

Business rules belong only here.

The Domain has zero framework dependencies.

Never import:

Spring

Hibernate

Jackson

MapStruct

JPA

Lombok

inside Domain.

---

# Aggregate Rules

Every Aggregate has one Aggregate Root.

Aggregate Root protects invariants.

Repositories load only Aggregate Roots.

Never expose child Entities directly.

Prefer small Aggregates.

Reference external Aggregates only by Identifier.

---

# Entity Rules

Entities have identity.

Entities own behavior.

Entities never become DTOs.

Entities never become database models.

Never expose Entities through REST.

---

# Value Object Rules

Immutable.

No identity.

Equality by value.

No setters.

Examples

BookTitle

EmailAddress

ISBN

ReadingDuration

ProgressPercentage

Prefer Value Objects over primitives.

---

# Repository Rules

Repository interfaces belong to Domain.

JpaRepository implementations belong to Infrastructure.

Repository responsibilities:

Load Aggregate

Save Aggregate

Nothing else.

Never implement business rules inside Repositories.

---

# Domain Service Rules

Domain Services exist only when behavior does not naturally belong to one Aggregate.

Avoid Service classes that only move logic out of Aggregates.

Prefer rich Domain Models.

---

# Application Layer

Responsible for:

Use Cases

Transactions

Command Handling

Query Handling

Publishing Events

Coordination

Application Layer is NOT business logic.

Business decisions belong to Domain.

---

# Infrastructure Layer

Responsible for:

Persistence

Messaging

External APIs

Storage

Search

Security Adapters

Configuration

Infrastructure must never define business behavior.

---

# Controller Rules

Controllers coordinate only.

Controllers:

Receive Request

Validate Request

Call Application

Return Response

Nothing more.

Never

Query Repository

Call Entity

Execute business logic

Open transactions

---

# DTO Rules

Always use DTOs.

Never expose Domain.

Never expose JPA Entity.

Every endpoint uses:

Request DTO

Response DTO

Validation DTO

DTOs never contain business behavior.

---

# Validation Rules

Syntax Validation

↓

API Layer

Business Validation

↓

Domain

Persistence Validation

↓

Infrastructure

Never mix them.

---

# Exception Rules

DomainException

↓

ApplicationException

↓

Problem Details

Never expose stack traces.

Never expose SQL exceptions.

Always return meaningful error responses.

---

# Logging Rules

Use SLF4J.

Meaningful logs only.

Never log:

Passwords

JWT Tokens

Secrets

Personal Sensitive Data

Prefer structured logging.

---

# Coding Principles

Readable > Clever

Explicit > Implicit

Simple > Complex

Maintainable > Short

Business Meaning > Technical Trick

Every class should communicate intent immediately.

# Spring Boot Rules

## Spring Boot Version

Always use the latest supported Spring Boot 3.x stable release.

Never use deprecated APIs.

Prefer Spring Boot conventions over custom configuration.

---

# Spring Modulith

VAJ is a Modular Monolith.

Every Feature is one logical module.

Modules communicate through:

- Application Services
- Domain Events
- Published Interfaces

Never access another module's internal packages.

Never bypass module boundaries.

---

# Configuration Rules

Configuration belongs only in:

config/

Never place configuration classes inside feature packages.

Use @Configuration only inside infrastructure or config modules.

---

# Dependency Injection

Always use constructor injection.

Never use field injection.

Never use @Autowired on fields.

Example

Good

public BookController(BookApplicationService service)

Bad

@Autowired
private BookApplicationService service;

---

# Transactions

Transactions belong only in the Application layer.

Good

Application Service

↓

@Transactional

↓

Aggregate

Bad

Controller

↓

@Transactional

Repository

↓

@Transactional

Domain

↓

@Transactional

---

# Persistence Rules

Persistence is an implementation detail.

Domain must never know JPA exists.

Infrastructure maps between:

Domain

↓

JPA Entity

Never use JPA Entities outside Infrastructure.

---

# JPA Entity Rules

JPA Entities exist only for persistence.

JPA Entity != Domain Entity

JPA Entity != API DTO

Never expose JPA Entities.

---

# Mapping Rules

Use MapStruct.

Mappings allowed:

Request DTO

↓

Domain

Domain

↓

Response DTO

Domain

↓

JpaEntity

JpaEntity

↓

Domain

Never map manually unless mapping contains business logic.

---

# Flyway Rules

Every database change requires a Flyway migration.

Never edit an executed migration.

Never combine unrelated changes into one migration.

Migration naming:

V001\_\_Create_Books.sql

V002\_\_Create_Authors.sql

V003\_\_Add_Book_Cover.sql

---

# SQL Rules

Always use explicit constraints.

Always define indexes intentionally.

Always use foreign keys when appropriate.

Avoid database triggers.

Avoid stored procedures unless explicitly required.

---

# Security Rules

Authentication

Spring Security

Authorization

Role Based

Password Hashing

BCrypt

Session

Stateless

Token

JWT Access Token

Refresh Token

---

# Authentication Rules

Controllers never authenticate manually.

Spring Security handles authentication.

Application receives authenticated UserId only.

Never pass SecurityContext into Domain.

---

# Authorization Rules

Authorization happens before entering the Domain.

Domain assumes the caller is already authorized.

Business permissions are validated inside the Domain when required.

---

# Password Rules

Never store plain passwords.

Never log passwords.

Always hash using BCrypt.

Never expose password hashes.

---

# JWT Rules

Use short-lived Access Tokens.

Use Refresh Tokens.

Validate signature.

Validate expiration.

Never trust client claims without validation.

---

# Validation Rules

Use Jakarta Validation for input validation.

Examples:

@NotBlank

@NotNull

@Email

@Size

Business validation belongs to Domain.

---

# API Design

REST only.

Plural resources.

Examples:

/books

/authors

/highlights

/reading-sessions

Use proper HTTP methods.

GET

POST

PATCH

DELETE

---

# Response Rules

Always return DTOs.

Never return:

Entities

JpaEntities

Exceptions

Stack traces

Use consistent response structures.

---

# OpenAPI Rules

Every endpoint must be documented.

Every Request DTO documented.

Every Response DTO documented.

Every error documented.

Swagger must always reflect the implementation.

---

# Error Handling

Use RFC 9457 Problem Details.

Every exception becomes a meaningful API response.

Never expose internal implementation details.

---

# Auditing

Every Aggregate supports auditing when required.

Typical fields:

CreatedAt

CreatedBy

UpdatedAt

UpdatedBy

DeletedAt

DeletedBy

Auditing is implemented in Infrastructure.

---

# Time Handling

Never call LocalDateTime.now() directly inside Domain.

Use a Clock abstraction.

This improves testability.

---

# Identifier Rules

Use UUID identifiers.

Identifiers are immutable.

Identifiers are generated by the Application layer unless business rules specify otherwise.

---

# File Storage

Domain never knows where files are stored.

Storage implementation belongs to Infrastructure.

Possible implementations:

Local

S3 Compatible

Azure Blob (Future)

---

# External Integrations

Never call external APIs directly from the Domain.

Always use interfaces.

Infrastructure provides implementations.

---

# Background Jobs

Background processing belongs to the Application layer.

Business rules remain inside the Domain.

Jobs must be idempotent whenever possible.

---

# Caching

Cache only Read Models.

Never cache Aggregates unless explicitly approved.

Cache invalidation must be deterministic.

---

# General Principle

Spring Boot is a framework.

The framework supports the Domain.

The Domain must never depend on the framework.

# CQRS Implementation

VAJ uses CQRS as an architectural guideline.

CQRS is used to separate responsibilities.

CQRS is NOT used to create unnecessary complexity.

---

# Command Side

Commands represent user intent.

Examples:

CreateBookCommand

UpdateBookCommand

DeleteBookCommand

StartReadingSessionCommand

CompleteReadingSessionCommand

CreateKnowledgeNoteCommand

Commands contain data only.

Commands never contain business logic.

---

# Command Handlers

Each Command has exactly one Handler.

Naming:

CreateBookCommand

↓

CreateBookHandler

Responsibilities:

- Validate command
- Load Aggregate
- Execute business operation
- Save Aggregate
- Publish Domain Events

Nothing else.

---

# Query Side

Queries never modify state.

Queries may use:

Read Models

Projections

Database Views

Optimized SQL

Examples:

GetBookQuery

SearchBooksQuery

GetDashboardQuery

GetStatisticsQuery

---

# Query Handlers

Each Query has exactly one Handler.

Responsibilities:

Read data

Map Response

Return DTO

Never execute business rules.

---

# Domain Events

Aggregates publish Events.

Events describe facts.

Events use past tense.

Examples:

BookCreated

ReadingSessionCompleted

HighlightCreated

KnowledgeNoteCreated

Never publish Events from Controllers.

Never publish Events from Repositories.

---

# Event Publishing

Events are published only after successful transaction commit.

Failed transactions produce no Events.

Events must be immutable.

---

# Policies

Policies consume Events.

Policies coordinate workflows.

Policies execute Commands.

Policies never modify Aggregates directly.

Example:

ReadingSessionCompleted

↓

UpdateReadingStateCommand

↓

EvaluateGoalCommand

↓

UpdateStatisticsProjectionCommand

---

# Projections

Projections are read models.

They:

Never own business rules.

May be rebuilt.

Consume Events.

Serve Queries.

Examples:

ReadingState

Statistics

Dashboard

SearchIndex

---

# Event Ordering

Ordering is guaranteed only inside one Aggregate.

Never assume ordering across Aggregates.

Consumers must be idempotent.

---

# Testing Strategy

Every Feature requires:

Aggregate Tests

Application Tests

Integration Tests

Architecture Tests

Controller Tests

Mapper Tests

---

# Unit Testing

Unit Tests verify business rules.

Mock Infrastructure.

Do not mock Domain behavior.

Prefer testing Aggregates directly.

---

# Integration Testing

Use Testcontainers.

Never rely on developer machines.

Database integration tests are mandatory for persistence logic.

---

# Architecture Testing

Use ArchUnit.

Verify:

Package boundaries

Dependency direction

No Spring in Domain

No JPA in Domain

No forbidden imports

Architecture violations fail the build.

---

# Performance Guidelines

Correctness first.

Performance second.

Measure before optimizing.

Prefer database indexes over premature caching.

Cache only Query Models.

---

# Concurrency

Aggregates should use optimistic locking when required.

Avoid pessimistic locking unless justified.

Business consistency belongs to Aggregates.

---

# Logging

Log business events.

Log failures.

Log integrations.

Never log secrets.

Never log passwords.

Never log JWT tokens.

---

# Review Checklist

Before completing a Feature:

[ ] Flyway migration created

[ ] Aggregate implemented

[ ] Value Objects immutable

[ ] Repository interface in Domain

[ ] Repository implementation in Infrastructure

[ ] Command implemented

[ ] Query implemented

[ ] Event implemented

[ ] Policy implemented (if needed)

[ ] REST API implemented

[ ] OpenAPI updated

[ ] Angular contract verified

[ ] Unit Tests passing

[ ] Integration Tests passing

[ ] Architecture Tests passing

[ ] Documentation updated

---

# Code Smells

Avoid:

Large Aggregates

Fat Controllers

God Services

Anemic Domain Models

Business Logic in Repositories

Business Logic in Controllers

Duplicate Validation

Primitive Obsession

Feature Envy

Long Parameter Lists

Hidden Side Effects

---

# Definition of Backend Done

Backend implementation is complete only when:

✓ Business Rules implemented

✓ DDD respected

✓ Clean Architecture respected

✓ CQRS respected

✓ Events published correctly

✓ Tests passing

✓ Static analysis clean

✓ Documentation updated

✓ No architecture violations

---

# Final Backend Rule

The Backend exists to protect the Domain.

Frameworks are replaceable.

Business rules are not.

Always optimize for long-term maintainability.

# Enterprise Development Rules

The backend is expected to live for many years.

Every implementation must prioritize:

Correctness

↓

Maintainability

↓

Readability

↓

Scalability

↓

Performance

Never reverse this order.

---

# SOLID Principles

Every implementation must follow:

Single Responsibility Principle

Open / Closed Principle

Liskov Substitution Principle

Interface Segregation Principle

Dependency Inversion Principle

Violations require architectural justification.

---

# DRY Principle

Avoid duplicated business logic.

Reuse Domain concepts.

Do not duplicate validation.

Do not duplicate mapping.

Shared business behavior belongs inside the Domain.

---

# KISS Principle

Prefer the simplest implementation.

Avoid unnecessary abstractions.

Avoid generic frameworks inside the project.

Avoid speculative architecture.

---

# YAGNI Principle

Implement only what is required.

Do not build future features.

Do not create extension points without business justification.

---

# Code Style

Methods should be small.

Classes should have one responsibility.

Public APIs should be explicit.

Private methods should improve readability.

Meaningful names are mandatory.

Avoid abbreviations.

---

# Method Rules

Prefer methods under 30 lines.

Extract business concepts into methods.

Avoid deeply nested conditions.

Return early when appropriate.

One method should express one intention.

---

# Class Rules

One class.

One responsibility.

Avoid Utility classes for business logic.

Avoid static business methods.

Avoid God Classes.

---

# Dependency Rules

High-level modules never depend on low-level implementations.

Depend on abstractions.

Infrastructure depends on Domain.

Domain depends on nothing.

---

# Security Checklist

Before completing any Feature verify:

[ ] Authentication required

[ ] Authorization verified

[ ] Input validated

[ ] Output sanitized

[ ] Secrets protected

[ ] Sensitive data not logged

[ ] JWT validated

[ ] SQL Injection prevented

[ ] XSS considered

[ ] CSRF strategy documented (if applicable)

---

# Performance Checklist

Before optimization ask:

Is it measurable?

Is it necessary?

Is it repeatable?

Do not optimize prematurely.

Prefer indexes before caching.

Prefer projections before complex joins.

Prefer simple code over micro-optimizations.

---

# Database Checklist

Every persistence change must verify:

[ ] Flyway migration exists

[ ] Constraints defined

[ ] Foreign keys reviewed

[ ] Indexes reviewed

[ ] Rollback strategy considered

[ ] Naming conventions respected

---

# API Checklist

Every endpoint must verify:

[ ] RESTful URL

[ ] Request DTO

[ ] Response DTO

[ ] Validation

[ ] OpenAPI documentation

[ ] Correct HTTP Status

[ ] RFC 9457 Problem Details

[ ] Pagination (if applicable)

[ ] Sorting (if applicable)

[ ] Filtering (if applicable)

---

# Testing Checklist

Every Feature must include:

[ ] Aggregate Tests

[ ] Application Tests

[ ] Integration Tests

[ ] Controller Tests

[ ] Mapper Tests

[ ] Architecture Tests

Coverage should prioritize business rules, not arbitrary percentages.

---

# Static Analysis

Code must pass:

Compilation

Unit Tests

Integration Tests

ArchUnit

Formatting

Checkstyle (if enabled)

SpotBugs/Sonar (if enabled)

No critical issues.

---

# AI Coding Rules

When generating code:

Generate complete implementations.

Never generate placeholder methods.

Never generate TODO comments.

Never leave unreachable code.

Never suppress warnings without justification.

Never ignore exceptions silently.

Always explain architectural decisions when requested.

---

# AI Refactoring Rules

Refactoring is allowed only if:

Behavior is preserved.

Tests remain green.

Architecture improves.

Readability improves.

No public contract is broken.

Otherwise, do not refactor.

---

# Backend Anti Patterns

Never create:

Fat Controllers

Fat Services

God Objects

Anemic Domain Models

Chatty Repositories

Shared Mutable State

Circular Dependencies

Business Logic in Controllers

Business Logic in Repositories

Business Logic in DTOs

Business Logic in Mappers

Framework-dependent Domain Models

---

# Review Contract

Before opening a Pull Request verify:

Architecture

[ ] DDD respected

[ ] Clean Architecture respected

[ ] CQRS respected

[ ] Module boundaries respected

Code

[ ] Readable

[ ] Consistent

[ ] No duplication

[ ] No dead code

[ ] No unnecessary abstraction

Testing

[ ] Unit Tests pass

[ ] Integration Tests pass

[ ] Architecture Tests pass

Documentation

[ ] OpenAPI updated

[ ] Feature documentation updated

[ ] BuildOrder progress updated

Deployment

[ ] Flyway migration reviewed

[ ] Configuration reviewed

[ ] No environment-specific code

---

# Engineering Principles

The framework serves the Domain.

The database serves the Domain.

The API serves the Domain.

The UI serves the Domain.

Everything exists to protect business rules.

---

# Final Backend Contract

Every generated backend implementation must comply with:

VAJ-CODEX.md

VAJ-BACKEND-CODEX.md

BuildOrder.md

Feature Specification

Current Task

If any conflict exists:

Priority:

1. Current Task

2. Feature Specification

3. BuildOrder

4. VAJ-BACKEND-CODEX.md

5. VAJ-CODEX.md

Never violate a higher priority document.

---

# Final Engineering Principle

Good software solves today's problem.

Great software solves today's problem without making tomorrow's problem harder.

VAJ is built for long-term evolution.

End of Document.
