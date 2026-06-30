# VAJ Development Workflow

Version: 1.0

---

# Purpose

This document defines the official software development workflow of VAJ.

Every feature must follow the same lifecycle.

The workflow exists to ensure:

- Consistency
- Quality
- Predictability
- AI-friendly development

---

# Feature Lifecycle

Idea

↓

Capability

↓

Specification

↓

Architecture Review

↓

Implementation

↓

Testing

↓

Code Review

↓

Documentation Update

↓

Merge

↓

Release

---

# Step 1 — Capability

Question:

What business capability are we adding?

Example

Book Export

Reading Statistics

Highlight Review

Never start from database tables.

Never start from controllers.

---

# Step 2 — Specification

Create the Feature Specification.

Describe:

Purpose

Business Rules

Use Cases

Events

Permissions

API

Database Impact

No implementation yet.

---

# Step 3 — Architecture Review

Before coding verify:

Aggregate

Dependency Rules

Transactions

Events

Permissions

No coding before architecture approval.

---

# Step 4 — Implementation

Order of implementation

Entity

↓

Repository

↓

Service

↓

Controller

↓

Mapper

↓

Validation

↓

Tests

↓

Documentation

Never change the order without reason.

---

# Step 5 — Testing

Required

Unit Tests

Integration Tests

Architecture Tests

Optional

Performance Tests

End-to-End Tests

No feature is complete without tests.

---

# Step 6 — Code Review

Verify

Business Rules

Architecture

Naming

Performance

Security

Tests

Documentation

---

# Step 7 — Documentation

Every feature change updates:

Feature Specification

OpenAPI

README (if needed)

Architecture (if affected)

---

# Step 8 — Merge

Merge only if:

✓ Tests pass

✓ Architecture passes

✓ Build passes

✓ Documentation updated

✓ No TODO

✓ No FIXME

---

# AI Workflow

Before generating code

Read:

PROJECT

ARCHITECTURE

DOMAIN

Feature Specification

API Rules

Backend Rules

Then generate.

Never generate code first.

---

# Definition of Done

A feature is Done only if:

Business Rules implemented

Tests written

Documentation updated

Architecture respected

Permissions verified

Events published

Logging added

Validation completed

No compilation errors

No failing tests

No TODOs
