# VAJ Build Order
Version: 2.0
Status: Active

---

# Purpose

This document is the official execution plan for building VAJ.

It defines:

- Build phases
- Execution order
- Dependencies
- Deliverables
- Acceptance criteria
- AI execution rules

No feature may be implemented before its prerequisites are complete.

---

# Development Strategy

Development is Feature-by-Feature.

Never Layer-by-Layer.

Each Feature must be fully completed before starting the next one.

Completed means:

✓ Database

✓ Backend

✓ API

✓ Frontend (Angular)

✓ Tests

✓ Documentation

---

# Global Workflow

Specification

↓

Architecture Review

↓

Database (Flyway)

↓

Domain

↓

Application

↓

Infrastructure

↓

REST API

↓

Angular

↓

Tests

↓

Review

↓

Merge

---

# Phase 00
Project Foundation

Goal

Prepare the entire development environment.

Tasks

FOUNDATION-001
Create Monorepo

FOUNDATION-002
Configure Git

FOUNDATION-003
Create Backend

FOUNDATION-004
Create Angular Workspace

FOUNDATION-005
Docker Compose

FOUNDATION-006
CI/CD

FOUNDATION-007
Coding Standards

Deliverables

backend/

frontend-angular/

docs/

infra/

tasks/

Acceptance

Backend starts

Angular starts

Swagger available

Docker works

GitHub Actions pass

---

# Phase 01
Shared Foundation

Goal

Build reusable infrastructure.

Tasks

COMMON-001
Common Module

COMMON-002
Exception Handling

COMMON-003
Audit

COMMON-004
Security Infrastructure

COMMON-005
OpenAPI

COMMON-006
Base Aggregate

COMMON-007
Base Entity

COMMON-008
Result Pattern

COMMON-009
Pagination

COMMON-010
Validation

Acceptance

Shared infrastructure ready.

No business feature implemented yet.

---

# Phase 02
Identity

Dependencies

Phase 01

Features

AUTH

USER

Deliverables

Register

Login

JWT

Refresh Token

Profile

Acceptance

Authentication works.

Authorization works.

Swagger complete.

Angular login page complete.

---

# Phase 03
Library

Dependencies

Identity

Features

Library

Library Settings

Acceptance

User can create libraries.

---

# Phase 04
Catalog

Dependencies

Library

Features

Book

Author

Publisher

Category

Acceptance

Books fully manageable.

Search by metadata.

---

# Phase 05
Reading

Dependencies

Catalog

Features

Reading Session

Bookmark

Reading Goal

Acceptance

Reading workflow complete.

---

# Phase 06
Knowledge

Dependencies

Reading

Features

Highlight

Knowledge Note

Collection

Tag

Acceptance

Knowledge workflow complete.

---

# Phase 07
Analytics

Dependencies

Knowledge

Features

Reading State

Statistics

Dashboard

Acceptance

All projections working.

---

# Phase 08
Platform

Dependencies

Analytics

Features

Search

Storage

Notification

Acceptance

Platform complete.

---

# Feature Workflow

Every Feature follows this sequence.

01

Database Migration

↓

02

Domain Model

↓

03

Application Layer

↓

04

Infrastructure

↓

05

REST API

↓

06

Angular UI

↓

07

Tests

↓

08

Documentation

↓

09

Review

---

# Task Template

Every task must define

Task Id

Purpose

Dependencies

Input Documents

Output Files

Files Allowed To Modify

Files Forbidden To Modify

Acceptance Criteria

Definition Of Done

Estimated Time

---

# AI Execution Rules

AI may modify only files listed in the task.

AI must never modify unrelated modules.

AI must generate tests.

AI must update documentation.

AI must follow:

VAJ-CODEX.md

Module Standards

Package Conventions

Naming Conventions

CQRS Guidelines

---

# Definition of Done

Feature is Done only when

Database Migration Complete

Backend Complete

REST API Complete

Angular UI Complete

OpenAPI Updated

Tests Passing

Documentation Updated

Code Review Passed

---

# Branch Strategy

main

Protected

develop

Integration

feature/<task-id>

Development

release/<version>

Release

hotfix/<issue>

Emergency Fix

---

# Commit Convention

feat(book): create aggregate

fix(reading): progress calculation

refactor(search): improve indexing

docs(build): update build order

test(highlight): add integration tests

---

# Release Milestones

M1

Foundation

M2

Identity

M3

Catalog

M4

Reading

M5

Knowledge

M6

Analytics

M7

Platform

M8

Release Candidate

M9

Version 1.0

---

# Success Criteria

Readable code.

Predictable architecture.

Repeatable AI execution.

High test coverage.

Stable releases.

Long-term maintainability.