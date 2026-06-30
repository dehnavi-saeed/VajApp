# VAJ Module Standards

Version: 1.0

---

# Purpose

This document defines the standard module structure for every feature in VAJ.

Every feature must follow the same layout.

No exceptions.

---

# Module Layout

feature-name/

    api/

    application/

    domain/

    infrastructure/

---

# API Layer

Responsibilities

Expose REST endpoints.

Validate requests.

Convert DTOs.

Return Responses.

Never contain business logic.

Contains

Controller

Request DTO

Response DTO

OpenAPI

Exception Mapping

---

# Application Layer

Responsibilities

Execute use cases.

Manage transactions.

Publish Domain Events.

Coordinate Domain Objects.

Never contain infrastructure code.

Contains

Command Handlers

Query Handlers

Application Services

Policies

Mappers

---

# Domain Layer

Responsibilities

Business Rules.

Aggregates.

Entities.

Value Objects.

Domain Services.

Repositories (Interfaces).

Contains

Aggregate Root

Entity

Value Object

Repository

Factory

Specification

Policy

Domain Event

---

# Infrastructure Layer

Responsibilities

Persistence.

Messaging.

External APIs.

Storage.

Search.

Contains

JPA Entity

Repository Implementation

MapStruct Mapper

Flyway

Configuration

Adapters

Clients

---

# Dependency Rules

API

↓

Application

↓

Domain

Infrastructure

↓

Domain

Application

↓

Infrastructure (Interfaces only)

Domain

↓

Nothing

---

# Package Structure

feature/

    api/

        controller/

        request/

        response/

    application/

        command/

        query/

        handler/

        service/

        mapper/

    domain/

        aggregate/

        entity/

        valueobject/

        repository/

        service/

        event/

        exception/

    infrastructure/

        persistence/

        mapper/

        configuration/

        client/

---

# Allowed Dependencies

Controller

↓

Application

Application

↓

Domain

Infrastructure

↓

Domain

---

# Forbidden Dependencies

Controller → Repository

Controller → Entity

Entity → Repository

Entity → Service

Domain → Spring

Domain → JPA

Domain → MapStruct

Application → Controller

---

# Repository Rules

Repositories belong to Domain.

Implementations belong to Infrastructure.

Never expose JPA outside Infrastructure.

---

# Mapping Rules

DTO ↔ Domain

MapStruct

Domain ↔ Entity

MapStruct

Never map inside Controller.

---

# Validation Rules

Request Validation

API Layer

Business Validation

Domain Layer

Persistence Validation

Infrastructure Layer

---

# Transaction Rules

Transactions exist only in Application Layer.

Never inside Controller.

Never inside Domain.

---

# Exception Rules

API

↓

ApplicationException

↓

DomainException

↓

InfrastructureException

---

# Event Rules

Aggregates publish Events.

Application publishes Events.

Infrastructure delivers Events.

---

# Testing Layout

feature/

    unit/

    integration/

    architecture/

---

# Naming

BookController

CreateBookCommand

CreateBookHandler

BookRepository

JpaBookRepository

BookMapper

BookEntity

BookCreatedEvent

---

# AI Rules

Always generate modules using this structure.

Never skip layers.

Never place Repository in API.

Never place Entity in Controller.

Never inject Repository into Aggregate.

Follow Dependency Rules strictly.
