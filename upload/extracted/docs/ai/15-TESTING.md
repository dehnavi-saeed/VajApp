# VAJ Testing Strategy

Version: 3.0

---

# Purpose

This document defines the testing strategy of VAJ.

Testing is not an afterthought.

Testing is part of software design.

Every feature must be designed with testability in mind.

---

# Philosophy

The goal of testing is confidence.

Not code coverage.

Not quantity of tests.

Confidence that the system behaves correctly.

---

# Testing Pyramid

                E2E
             Integration
          Architecture Tests
             Unit Tests

Most tests should be Unit Tests.

Few should be End-to-End.

---

# Test Types

VAJ uses five categories of tests.

- Unit Tests
- Integration Tests
- Architecture Tests
- Contract Tests
- End-to-End Tests

---

# Unit Tests

Purpose

Verify business behavior.

Scope

One class.

Dependencies should be mocked.

Characteristics

Fast

Deterministic

Independent

Readable

---

# Integration Tests

Purpose

Verify interaction between components.

Examples

Service + Database

Repository + SQL Server

REST + Security

Flyway + Database

Use Testcontainers.

Never rely on developer machines.

---

# Architecture Tests

Architecture is executable.

Use ArchUnit.

Verify:

Layer rules

Dependency rules

Package rules

Naming conventions

Forbidden dependencies

Architecture tests protect the project.

---

# Contract Tests

Public APIs must remain stable.

Verify:

Response structure

Status codes

Headers

Error model

Pagination

Sorting

Filtering

---

# End-to-End Tests

Verify complete user workflows.

Examples

Register

Login

Create Library

Add Book

Start Reading

Create Highlight

Complete Goal

End-to-End tests should be minimal.

---

# Naming

Test classes mirror production classes.

BookServiceTest

ReadingServiceTest

HighlightControllerTest

Test methods describe behavior.

Examples

shouldCreateBook()

shouldRejectDuplicateLibraryName()

shouldUpdateReadingProgress()

shouldArchiveBook()

---

# Test Data

Prefer builders.

Avoid magic values.

Each test owns its data.

Tests must not depend on execution order.

---

# Assertions

Assert business behavior.

Avoid asserting implementation details.

Test outcomes.

Not internal variables.

---

# Mocking

Mock external systems.

Do not mock business logic.

Avoid excessive mocking.

---

# Database Tests

Always use Testcontainers.

Never depend on a shared database.

Every test should be isolated.

---

# Time

Never depend on the system clock.

Inject Clock.

Tests must remain deterministic.

---

# Randomness

Avoid randomness.

If random values are required,

use deterministic seeds.

---

# Performance Tests

Critical operations should define acceptable performance targets.

Examples

Search

Book Import

Reading Statistics

Large Library Loading

---

# Code Coverage

Coverage is a metric.

Not a goal.

High coverage does not guarantee quality.

Meaningful tests are preferred.

---

# AI Rules

When generating code:

Generate tests for every public Service.

Generate architecture tests for new architectural rules.

Generate integration tests for persistence.

Prefer readable tests over clever tests.

Tests are production code.
