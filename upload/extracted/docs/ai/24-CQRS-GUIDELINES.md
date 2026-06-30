# VAJ CQRS Guidelines

Version: 1.0

---

# Purpose

This document defines the official CQRS rules for VAJ.

CQRS in VAJ is a design guideline.

It is not mandatory to split databases or services.

The goal is separation of responsibilities.

---

# Principles

Commands change state.

Queries read state.

Commands never return read models.

Queries never modify business data.

---

# Command Side

Responsible for:

Business Rules

Validation

Aggregates

Domain Events

Transactions

Examples

Create Book

Create Highlight

Finish Reading Session

Create Knowledge Note

Delete Collection

---

# Query Side

Responsible for:

Read Models

Statistics

Dashboard

Search

Reports

Fast Queries

Examples

Reading State

Dashboard

Search Results

Reading Statistics

Knowledge Statistics

---

# Aggregate Rules

Create an Aggregate only if:

Business Rules exist.

Consistency is required.

Transactions are required.

State changes exist.

Otherwise

Use Projection.

---

# Projection Rules

Projection owns no business data.

Projection may be rebuilt.

Projection consumes Events.

Projection is optimized for reading.

Projection never publishes Domain Events.

---

# Command Rules

Commands

Represent user intent.

Contain only required information.

Must be validated.

Never contain business logic.

Example

CreateBook

FinishReadingSession

CreateHighlight

---

# Query Rules

Queries never update state.

Queries may use projections.

Queries should be optimized.

Queries may join multiple sources.

---

# Event Rules

Aggregates publish Events.

Queries consume Events.

Policies consume Events.

Platform consumes Events.

Controllers never publish Events.

---

# Transaction Rules

Transactions exist only on Command Side.

Queries are transaction-free.

---

# Dependency Rules

Controller

↓

Application Service

↓

Aggregate

↓

Domain Event

↓

Policy

↓

Projection

Never reverse this dependency.

---

# Consistency Rules

Command Side

Strong consistency.

Projection Side

Eventual consistency.

Users should never notice projection delays.

---

# Performance Rules

Optimize Queries.

Protect Commands.

Never sacrifice correctness for speed.

Cache only Read Models.

---

# Testing Rules

Command Tests

Business Rules

Aggregate Tests

Domain Service Tests

Policy Tests

Projection Tests

Query Tests

Performance Tests

---

# Anti Patterns

Do NOT

Return Entity from API.

Update Projection directly.

Modify Aggregate from Query.

Share Aggregate between Contexts.

Place business logic inside Controllers.

Use Repository inside Projection.

Duplicate business rules.

---

# AI Rules

Generate Commands first.

Generate Aggregates second.

Generate Events third.

Generate Policies fourth.

Generate Projections fifth.

Generate Queries last.

Never start from Controllers.

Never start from Database.

Always start from Domain.

---

# Definition of Done

Every new feature must include:

Command

Aggregate

Domain Event

Policy

Projection (if needed)

REST API

Tests

Documentation

No feature is complete without following CQRS rules.
