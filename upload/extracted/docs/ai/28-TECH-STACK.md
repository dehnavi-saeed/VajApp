# VAJ Technology Stack

Version: 1.0

---

# Purpose

Defines the official technology stack of VAJ.

Technology choices are standardized.

Alternative technologies require an ADR.

---

# Backend

Language

Java 21 LTS

Framework

Spring Boot 3.5+

Architecture

Spring Modulith

Build

Maven

---

# Frontend

Framework

Angular 20+

Language

TypeScript

UI

Angular Material

CSS

Tailwind CSS

Charts

ApexCharts

Icons

Material Symbols

---

# Database

Primary

SQL Server 2022

Migration

Flyway

ORM

Hibernate 6

Spring Data JPA

---

# Security

Spring Security

JWT

Refresh Token

BCrypt

Role-Based Authorization

---

# API

REST

OpenAPI

Swagger UI

Problem Details (RFC 9457)

---

# Mapping

MapStruct

No manual mapping except special cases.

---

# Validation

Jakarta Validation

Business validation inside Domain.

---

# Search

Version 1

SQL Server Full Text Search

Version 2

OpenSearch / Elasticsearch

---

# Cache

Version 1

Caffeine

Version 2

Redis

---

# Storage

Local Storage

S3 Compatible Storage (Future)

---

# Messaging

Spring Events (v1)

RabbitMQ / Kafka (Future)

---

# Testing

JUnit 5

Mockito

Testcontainers

ArchUnit

---

# Monitoring

Micrometer

Prometheus

Grafana

---

# Logging

SLF4J

Logback

Structured JSON Logs

---

# Build

Maven

Wrapper required.

---

# Version Control

Git

GitHub

Git Flow (simplified)

---

# CI/CD

GitHub Actions

Build

Test

Static Analysis

Docker Build

---

# Docker

Docker Compose

Separate profiles

Development

Testing

Production

---

# Code Quality

Spotless

Checkstyle

SonarQube (Future)

---

# AI Development

OpenAI Codex

Claude Code

Gemini CLI

All generated code must follow:

VAJ-CODEX.md

Module Standards

Package Conventions

Naming Conventions

---

# Forbidden

No Lombok in Domain.

No Spring annotations in Domain.

No JPA annotations in Domain.

No business logic in Controllers.

No direct Entity exposure.

---

# Upgrade Policy

Java

LTS only.

Angular

Latest Stable.

Spring Boot

Latest Stable Minor.

Dependencies updated monthly.
