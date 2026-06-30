```markdown
# VAJ-CODEX.md

Version: 2.0
Status: Official
Project: VAJ
Last Updated: 2026

---

# Purpose

This document is the official AI development guide for VAJ.

Every AI assistant (Codex, Claude Code, Gemini CLI, Cursor, Windsurf or any future coding agent) MUST follow the rules defined in this document.

This document has higher priority than generated code.

Whenever generated code conflicts with this document, this document wins.

---

# Project Identity

Project Name

VAJ

Project Type

AI-First Personal Knowledge Management System

Primary Goal

Help users transform reading into structured knowledge.

Secondary Goals

- Organize knowledge
- Build long-term learning habits
- Keep knowledge searchable
- Produce maintainable software
- Enable AI-assisted development

---

# Product Vision

VAJ is NOT:

- an ERP
- a CRM
- a Wiki
- a CMS
- a Social Network
- a File Manager

VAJ IS:

A Personal Knowledge Management platform centered around books, reading and knowledge.

Books are only the starting point.

Knowledge is the real product.

---

# Development Philosophy

Development follows these principles:

Documentation First

Architecture First

Domain First

AI First

Feature First

Quality First

Never Database First.

Never UI First.

Never Controller First.

---

# Architecture

VAJ uses

- Modular Monolith
- Domain Driven Design
- Clean Architecture
- CQRS Friendly
- Event Driven
- Feature Based Modules
- API First
- Documentation Driven Development

---

# Architectural Principles

Every module owns its business rules.

Every module owns its data.

Communication happens through:

- Events
- Queries
- Identifiers

Never by sharing business objects.

---

# Bounded Contexts

Identity

Library

Catalog

Reading

Knowledge

Analytics

Platform

ReferenceData

Each context owns its own business logic.

No context may bypass another context.

---

# Technology Stack

Backend

Java 21

Spring Boot 3.x

Spring Modulith

SQL Server

Flyway

MapStruct

Spring Security

OpenAPI

Frontend

Angular

Angular Material

Tailwind CSS

TypeScript

Infrastructure

Docker Compose

GitHub Actions

---

# Source of Truth

The official project documentation is:

1. VAJ-MASTER-PLAN.md

2. BuildOrder.md

3. Feature Specifications

4. This document

Generated code is never considered the source of truth.

---

# AI Development Workflow

Before generating any code the AI MUST read

1.

VAJ-MASTER-PLAN.md

↓

2.

BuildOrder.md

↓

3.

Current Feature Specification

↓

4.

Current Task

↓

5.

VAJ-CODEX.md

Only then may code generation begin.

---

# AI Responsibilities

AI assists development.

AI does NOT design architecture.

AI does NOT invent business rules.

AI follows existing specifications.

If specifications are missing,

AI must ask for clarification.

Never guess business behavior.

---

# Golden Rules

Rule 1

Architecture is more important than speed.

Rule 2

Business Rules belong to the Domain.

Rule 3

Controllers coordinate only.

Rule 4

Database never defines the Domain.

Rule 5

Every Feature must be independently understandable.

Rule 6

Readable code is preferred over clever code.

Rule 7

Consistency is more important than personal style.

Rule 8

Generated code must be production quality.

Rule 9

Every change must be testable.

Rule 10

Never sacrifice maintainability.

---

# Coding Philosophy

Prefer

Explicit

Simple

Readable

Predictable

Maintainable

Avoid

Magic

Hidden behavior

Complex abstractions

Premature optimization

Duplicated business rules

---

# Documentation Driven Development

Implementation follows documentation.

Documentation does not follow implementation.

Whenever documentation and code disagree,

Documentation wins.

The implementation must be corrected.

---

# Build Strategy

Development is Feature-by-Feature.

Never Layer-by-Layer.

Each Feature is fully completed before starting another Feature.

Completed means:

- Database
- Domain
- Application
- Infrastructure
- API
- Frontend
- Tests
- Documentation

---

# AI Scope

AI only modifies files allowed by the current task.

AI never changes unrelated modules.

AI never performs architecture refactoring without explicit instruction.

AI never renames modules automatically.

AI never changes package structure unless requested.

---

# Communication Rules

If requirements are ambiguous:

Stop.

Explain the ambiguity.

Ask for clarification.

Never invent missing requirements.

---

# Quality Goals

Generated code must be

Readable

Consistent

Modular

Testable

Maintainable

Reviewable

Production Ready

---

# Long-Term Goal

VAJ is expected to evolve for many years.

Every implementation decision must prioritize long-term maintainability over short-term convenience.
```

# DDD Rules

## Domain First

Always design the Domain before writing infrastructure code.

The Domain Model is the heart of the application.

Business rules belong only in the Domain layer.

---

## Aggregates

Aggregates protect business consistency.

Every Aggregate has exactly one Aggregate Root.

Only the Aggregate Root may be loaded through a Repository.

Never expose internal Entities directly.

Aggregates enforce invariants.

Aggregates are small.

Aggregates should not reference other Aggregates directly.

Reference other Aggregates only by their Identifier.

---

## Entities

Entities have identity.

Identity never changes.

Entities contain behavior.

Entities are not DTOs.

Entities are not JPA models.

Entities are not API models.

---

## Value Objects

Value Objects are immutable.

Value Objects have no identity.

Equality is based on value.

Prefer Value Objects over primitive types whenever business meaning exists.

Examples:

- BookTitle
- ISBN
- EmailAddress
- ReadingDuration
- ProgressPercentage

---

## Domain Services

Use Domain Services only when business behavior cannot naturally belong to a single Aggregate.

Never create Service classes to avoid writing behavior inside Aggregates.

Avoid Anemic Domain Models.

---

## Repository Rules

Repositories belong to the Domain.

Repository implementations belong to Infrastructure.

Repositories load and save Aggregates.

Repositories never return JPA Entities.

Repositories never contain business logic.

---

# Clean Architecture Rules

Dependency direction is always inward.

API

↓

Application

↓

Domain

Infrastructure

↓

Domain

The Domain depends on nothing.

The Domain must never import:

- Spring
- JPA
- Hibernate
- MapStruct
- Jackson
- Lombok (Domain ممنوع)

---

# Layer Responsibilities

## API

Responsible for:

- HTTP
- Validation
- Authentication
- DTO conversion
- Status Codes

Never contains business logic.

---

## Application

Responsible for:

- Use Cases
- Transactions
- Command handling
- Query handling
- Coordination
- Publishing Domain Events

Never contains persistence details.

---

## Domain

Responsible for:

- Business Rules
- Aggregates
- Entities
- Value Objects
- Specifications
- Domain Services
- Domain Events

No framework dependencies.

---

## Infrastructure

Responsible for:

- Database
- JPA
- Flyway
- Messaging
- External APIs
- Storage
- Search
- Security adapters

Never contains business decisions.

---

# CQRS Rules

Commands change state.

Queries read state.

Commands never return Read Models.

Queries never modify state.

Use separate models for writing and reading when appropriate.

CQRS is used for separation of responsibilities, not for unnecessary complexity.

---

## Commands

Commands represent user intent.

Examples:

CreateBookCommand

StartReadingSessionCommand

CreateKnowledgeNoteCommand

Commands contain input data only.

Commands contain no business logic.

---

## Queries

Queries retrieve data.

Queries may read Projections.

Queries may join multiple data sources.

Queries never update the database.

---

# Projection Rules

Projection is read-only.

Projection owns no business rules.

Projection may be rebuilt at any time.

Projection consumes Events.

Projection never publishes Domain Events.

Examples:

ReadingState

Statistics

Dashboard

Search Index

---

# Event Rules

Domain Events represent facts.

Events are immutable.

Events use past tense.

Examples:

BookCreated

ReadingSessionCompleted

HighlightCreated

Events are published only after successful transaction completion.

Never publish Events from Controllers.

Never mutate an Event after creation.

---

# Policy Rules

Policies react to Domain Events.

Policies coordinate workflows.

Policies trigger Commands.

Policies never update Aggregates directly.

Policies must be idempotent.

---

# Dependency Rules

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

Controller → Repository

Controller → Entity

Entity → Repository

Domain → Spring

Domain → JPA

Application → Controller

Infrastructure → API

---

# Module Rules

Every Feature uses exactly the same layout:

feature/

api/

application/

domain/

infra/

No custom layouts.

No exceptions.

---

# Feature Ownership

Each Feature owns:

- API
- Application
- Domain
- Infrastructure

No Feature may own another Feature.

Communication happens only through:

- Commands
- Queries
- Events
- Identifiers

---

# Business Rules

Business Rules live only inside the Domain.

Never duplicate business rules.

Never place business rules inside:

- Controller
- Service Adapter
- Repository
- Angular Component

---

# Transaction Rules

Transactions belong to the Application layer.

Aggregates do not manage transactions.

Controllers do not manage transactions.

Repositories do not manage transactions.

---

# Error Handling

Business errors become Domain Exceptions.

Application translates Domain Exceptions.

API returns RFC 9457 Problem Details.

Never expose internal exception details to clients.

---

# Consistency Rules

Command Side

Strong Consistency.

Query Side

Eventual Consistency.

Prefer correctness over speed.

---

# Architecture Rule

If a design decision violates DDD, Clean Architecture or CQRS,

stop implementation,

review the architecture,

and update the design before writing code.

# Development Workflow

Every Feature must be implemented using the same execution flow.

Never skip a step.

---

## Standard Workflow

1. Read Specification

↓

2. Read BuildOrder

↓

3. Read Current Task

↓

4. Implement Flyway Migration

↓

5. Implement Domain

↓

6. Implement Application

↓

7. Implement Infrastructure

↓

8. Implement REST API

↓

9. Implement Angular UI

↓

10. Implement Tests

↓

11. Update Documentation

↓

12. Self Review

---

# Code Generation Rules

Always generate code in this order:

1. Migration

2. Domain

3. Application

4. Infrastructure

5. API

6. Frontend

7. Tests

Never start with Controllers.

Never start with Database Entities.

Never start with Angular Components.

---

# Feature Completion Order

Every Feature must include:

Database

↓

Backend

↓

API

↓

Frontend

↓

Tests

↓

Documentation

A partially implemented Feature is considered incomplete.

---

# Database Rules

Use Flyway only.

Never modify existing migration files.

Create one migration per task.

Migration filenames must be incremental.

Database schema changes must be backward compatible whenever possible.

---

# Backend Rules

Generate:

Aggregate

Value Objects

Entities

Repository Interface

Commands

Queries

Application Service

Event

Policy (if required)

Infrastructure

Controller

DTOs

Never skip the Domain layer.

---

# Frontend Rules

Generate:

Models

Services

Routes

Components

Forms

Validation

Guards (if required)

Angular implementation must follow the backend contract.

Never hardcode API responses.

---

# API Rules

Every API must include:

Validation

Problem Details (RFC 9457)

OpenAPI documentation

Request DTO

Response DTO

Pagination when applicable

Filtering when applicable

Sorting when applicable

Versioning support

---

# Testing Rules

Every Feature requires:

Unit Tests

Integration Tests

Architecture Tests (where applicable)

Test names must clearly describe business behavior.

Example:

shouldCreateBook()

shouldRejectDuplicateISBN()

shouldCompleteReadingSession()

---

# Documentation Rules

Every completed task updates:

Feature Specification (if behavior changes)

Swagger / OpenAPI

BuildOrder (progress)

Task Status

README (if required)

Never leave documentation outdated.

---

# Git Rules

One Task

↓

One Branch

↓

One Pull Request

Branch Naming

feature/BOOK-001

feature/READING-003

fix/BOOK-014

hotfix/AUTH-002

---

# Commit Rules

Use Conventional Commits.

Examples:

feat(book): implement aggregate

feat(reading): add session commands

fix(search): correct ranking

refactor(library): simplify validation

docs(codex): update AI rules

test(highlight): add integration tests

---

# Pull Request Rules

Every Pull Request must include:

Purpose

Scope

Files Changed

Screenshots (Frontend)

Migration Notes

Breaking Changes

Test Results

Checklist

---

# Definition of Done

A task is Done only if:

✓ Business Rules implemented

✓ Domain complete

✓ API complete

✓ Angular complete

✓ Tests passing

✓ Documentation updated

✓ Swagger updated

✓ Flyway migration added

✓ Code reviewed

✓ No TODOs

✓ No compilation warnings

---

# Quality Gates

Every merge must satisfy:

Build Success

Unit Tests Pass

Integration Tests Pass

Architecture Rules Pass

No Critical Static Analysis Issues

OpenAPI Valid

Angular Build Success

---

# AI Restrictions

AI MUST NOT:

Invent requirements.

Modify unrelated Features.

Delete existing functionality.

Change Architecture.

Ignore Specifications.

Generate placeholder implementations.

Leave TODO comments as final code.

Generate dead code.

Skip tests.

---

# Self Review Checklist

Before finishing a task, verify:

[ ] Business rules implemented correctly

[ ] No duplicated logic

[ ] DDD rules respected

[ ] CQRS rules respected

[ ] Naming conventions respected

[ ] Package structure respected

[ ] Tests written

[ ] Documentation updated

[ ] API documented

[ ] Frontend aligned with backend

---

# Performance Guidelines

Optimize only after correctness.

Avoid premature optimization.

Prefer readability.

Measure before optimizing.

Cache only Read Models.

---

# Security Guidelines

Never trust client input.

Validate every request.

Authorize every protected operation.

Never expose stack traces.

Never log sensitive data.

Hash passwords using BCrypt.

Use JWT access tokens with refresh tokens.

---

# Logging Rules

Log meaningful business events.

Avoid excessive logging.

Never log:

Passwords

Tokens

Secrets

Personal sensitive information

---

# Review Philosophy

Every implementation must answer:

Is it correct?

Is it readable?

Is it maintainable?

Is it testable?

Is it aligned with the architecture?

If any answer is "No",

the implementation is not complete.

# AI Best Practices

AI exists to accelerate development.

AI does not replace software architecture.

AI does not replace business analysis.

AI does not replace code review.

Every generated change must be understandable by a human developer.

Readable code is always preferred over shorter code.

---

# Enterprise Coding Principles

Every implementation must satisfy:

Correctness

↓

Readability

↓

Maintainability

↓

Testability

↓

Performance

Performance is never the first priority.

Correctness always comes first.

---

# SOLID Principles

All implementations should follow:

Single Responsibility Principle

Open / Closed Principle

Liskov Substitution Principle

Interface Segregation Principle

Dependency Inversion Principle

Violations require explicit architectural justification.

---

# DRY Principle

Avoid duplicated business logic.

Prefer reusable domain concepts.

Do not create utility classes for business rules.

---

# KISS Principle

Prefer the simplest implementation that satisfies the business requirements.

Do not introduce unnecessary abstractions.

Avoid speculative architecture.

---

# YAGNI Principle

Do not implement future requirements.

Only implement what is currently specified.

Future extensibility is good.

Unused functionality is not.

---

# Refactoring Rules

Refactoring is allowed only when it:

Improves readability

Improves maintainability

Removes duplication

Simplifies the design

Preserves behavior

Refactoring must not change business behavior.

---

# Backward Compatibility

Public APIs should remain backward compatible whenever possible.

Breaking API changes require:

Documentation update

Versioning strategy

Migration notes

Explicit approval

---

# Technical Debt

Avoid introducing technical debt.

If unavoidable:

Document it.

Track it.

Plan its removal.

Never hide technical debt.

---

# Anti Patterns

Never create:

God Objects

Anemic Domain Models

Fat Controllers

Transaction Scripts

Utility Classes for Domain Logic

Static Business Services

Shared Mutable State

Circular Dependencies

Hidden Side Effects

Repository Calls from Controllers

JPA Entities exposed through REST

Domain Objects coupled to Frameworks

---

# AI Anti Patterns

AI must never:

Invent new architecture

Invent business requirements

Ignore documentation

Generate placeholder code

Generate fake implementations

Leave TODO as production code

Silently change business behavior

Remove tests

Skip validation

Skip error handling

Skip documentation

Modify unrelated modules

---

# Long-Term Evolution

Every implementation should support future growth.

Prefer extension over modification.

Favor modularity.

Protect domain boundaries.

Avoid coupling between Features.

---

# Documentation Policy

Documentation is part of the implementation.

A Feature without documentation is incomplete.

Documentation must evolve together with the code.

---

# Architecture Review

Before implementing any Feature, verify:

Architecture boundaries

Module ownership

Dependency direction

DDD compliance

CQRS compliance

Security implications

Performance implications

If any issue exists,

resolve it before implementation.

---

# AI Decision Rules

When multiple implementations are possible:

Choose the one that:

Best matches the specification.

Respects the architecture.

Produces simpler code.

Improves readability.

Requires less maintenance.

Never choose cleverness over clarity.

---

# Review Checklist

Every completed task must answer YES to all questions.

Architecture

[ ] Does the implementation respect Modular Monolith boundaries?

[ ] Does it respect DDD?

[ ] Does it respect CQRS?

Domain

[ ] Are business rules inside the Domain?

[ ] Are Value Objects immutable?

[ ] Are Aggregates protecting invariants?

Application

[ ] Are transactions in the Application layer?

[ ] Are Commands and Queries separated?

Infrastructure

[ ] Is persistence isolated?

[ ] Are external services abstracted?

API

[ ] Are DTOs used?

[ ] Is validation implemented?

[ ] Is OpenAPI updated?

Frontend

[ ] Is Angular following project conventions?

[ ] Is UI consistent?

Testing

[ ] Are Unit Tests present?

[ ] Are Integration Tests passing?

Documentation

[ ] Are specifications updated?

[ ] Is BuildOrder updated?

[ ] Are migration notes documented?

---

# Definition of Excellence

Good code compiles.

Better code is tested.

Great code is understandable.

Excellent code remains understandable after five years.

VAJ aims for excellent code.

---

# Final AI Contract

By generating code for VAJ, the AI agrees to follow every rule defined in:

VAJ-CODEX.md

Module Standards

Package Conventions

Naming Conventions

BuildOrder

Feature Specifications

Current Task

If any conflict exists,

the following priority order applies:

1. Current Task

2. Feature Specification

3. BuildOrder

4. VAJ-CODEX.md

5. Other Standards

Never violate a higher priority document.

---

# Final Principle

The objective of VAJ is not to generate code.

The objective is to build a maintainable, scalable and understandable software system.

Every design decision should support that objective.

End of Document.
