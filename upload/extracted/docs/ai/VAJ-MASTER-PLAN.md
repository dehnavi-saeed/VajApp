# VAJ Master Plan

Version: 1.0

Status: Active

---

# What is VAJ?

VAJ is an AI-First Personal Knowledge Management System focused on reading, learning and knowledge building.

VAJ is NOT just a book tracker.

Books are only one source of knowledge.

The real goal is to help users transform reading into structured knowledge.

---

# Product Vision

Capture Knowledge.

Organize Knowledge.

Retrieve Knowledge.

Grow Knowledge.

---

# Guiding Principles

Domain First

AI First

Feature Based

DDD

Clean Architecture

CQRS Friendly

Event Driven

Modular Monolith

API First

Documentation Driven

---

# Architecture

Identity

Library

Catalog

Reading

Knowledge

Analytics

Platform

Reference Data

Each Context owns its own business rules.

Communication happens through Events, Queries or Identifiers.

---

# Technology

Backend

Java 21

Spring Boot

Spring Modulith

Maven

SQL Server

Flyway

MapStruct

Frontend

Angular

Angular Material

Tailwind

Infrastructure

Docker

GitHub Actions

OpenAPI

---

# Repository Structure

vaj/

    docs/

    backend/

    frontend-angular/

    infra/

    tasks/

---

# Development Workflow

Specification

↓

Architecture Review

↓

Task Definition

↓

Backend

↓

Frontend

↓

Tests

↓

Review

↓

Merge

---

# AI Workflow

Read

Master Plan

↓

Read

VAJ-CODEX.md

↓

Read

Feature Specification

↓

Read

Task

↓

Generate Code

↓

Run Tests

↓

Review

---

# Development Rules

Never skip Tests.

Never violate DDD boundaries.

Never violate Module Standards.

Never bypass CQRS rules.

Never modify unrelated modules.

---

# Documentation Order

Master Plan

↓

Architecture

↓

Standards

↓

Feature Specification

↓

Build Order

↓

Tasks

---

# Current Status

Architecture

Completed

Coding Standards

Completed

Feature Specifications

Completed

Build Plan

Pending

Implementation

Not Started

---

# MVP

Identity

Library

Book

Reading Session

Bookmark

Highlight

Knowledge Note

Collection

Dashboard

Search

---

# Version Roadmap

1.0

Core Platform

1.1

Import / Export

Backup

Performance

1.2

AI Assistant

Flashcards

2.0

Knowledge Graph

Semantic Search

Recommendations

---

# Definition of Done

Every Feature must include

Specification

Backend

Frontend

Tests

Swagger

Flyway

Documentation

Review

---

# AI Rules

Generate only requested Features.

Never generate unrelated code.

Follow Module Standards.

Follow Package Conventions.

Follow Naming Conventions.

Always update Tests.

Always update Documentation.

---

# Success Criteria

Readable Code.

Testable Code.

Maintainable Code.

Documented Code.

Predictable Architecture.

Long-term Evolution.
