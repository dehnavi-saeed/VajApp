# VAJ Dependency Rules

Version: 3.0

---

# Purpose

This document defines the dependency rules of VAJ.

A dependency is a one-way relationship.

Dependencies must remain intentional.

Incorrect dependencies create:

- Tight coupling
- Circular references
- Difficult testing
- Difficult maintenance
- Difficult modularization

Architecture is protected by dependency rules.

---

# Dependency Direction

Dependencies always flow inward.

Presentation

↓

Application

↓

Domain

↓

Infrastructure

Infrastructure never owns business rules.

---

# Layer Rules

Controller

↓

Service

↓

Repository

↓

Database

Controllers never access Repositories directly.

Repositories never call Services.

Repositories never call Controllers.

Entities never depend on Controllers.

Entities never depend on Spring MVC.

---

# Feature Dependencies

Every feature owns its own business logic.

Features communicate only through public Services.

Good

ReadingService

↓

BookService

Bad

ReadingRepository

↓

BookRepository

Never access another feature's Repository.

---

# Package Visibility

Public

Controller

Service

Private

Repository

Entity

Mapper

Validation

Exception

External features should never depend on implementation details.

---

# Common Package

The common package provides infrastructure.

Examples

Security

Logging

Audit

Configuration

Events

Responses

Exceptions

Business logic must never move into common.

---

# Allowed Dependencies

Controller

↓

DTO

↓

Service

↓

Mapper

↓

Repository

↓

Entity

---

# Forbidden Dependencies

Entity

↓

Controller

Entity

↓

Repository

Repository

↓

Controller

DTO

↓

Repository

DTO

↓

Entity

Controller

↓

Database

---

# Cross-Feature Rules

Book

may use

LibraryService

Book

must not use

LibraryRepository

Reading

may use

BookService

Reading

must not use

BookRepository

Notification

may consume

Domain Events

Notification

must not modify

Book Aggregate

Statistics

may consume

Reading Events

Statistics

must never modify

Reading Sessions

---

# Aggregate Boundaries

Each Aggregate protects itself.

Other Aggregates communicate through:

Identifiers

Services

Domain Events

Never manipulate another Aggregate's internal state.

---

# Event Dependencies

Publishers know nothing about consumers.

Consumers subscribe to events.

Good

BookCreated

↓

Statistics

↓

Notification

↓

Search Index

Bad

BookService

↓

NotificationService

↓

StatisticsService

Avoid direct orchestration when events are appropriate.

---

# Circular Dependencies

Circular dependencies are forbidden.

Examples

Book

↓

Reading

↓

Book

❌

Resolve by:

Service abstraction

Domain Events

Identifiers

---

# Utility Classes

Utility classes must remain stateless.

Utility classes must not contain business logic.

If business knowledge exists,

it belongs in a Service.

---

# Framework Dependencies

Spring belongs to the infrastructure layer.

Business logic should remain framework-independent whenever practical.

---

# Testing Dependencies

Tests may access implementation details.

Production code may not.

---

# AI Rules

When generating code:

Never introduce circular dependencies.

Prefer dependency inversion.

Prefer Domain Events.

Prefer Service collaboration.

Protect Aggregate boundaries.

Never bypass architectural layers.

Architecture correctness is more important than shorter code.
