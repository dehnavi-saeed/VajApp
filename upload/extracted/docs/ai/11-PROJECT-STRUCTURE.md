# VAJ Project Structure

Version: 3.0

---

# Purpose

This document defines the physical structure of the VAJ source code.

A predictable structure improves:

- Readability
- Discoverability
- Maintainability
- AI-assisted development
- Onboarding

The structure must remain stable over time.

---

# Repository Structure

vaj/

├── docs/

├── backend/

├── frontend/

├── docker/

├── scripts/

├── tools/

├── .github/

├── README.md

└── LICENSE

---

# Backend Structure

backend/

├── src/

│   ├── main/

│   │   ├── java/

│   │   └── resources/

│   └── test/

└── pom.xml

---

# Root Package

app.vaj

Never create additional root packages.

All source code belongs under:

app.vaj

---

# Main Package Structure

app.vaj

├── auth

├── user

├── library

├── book

├── author

├── publisher

├── reading

├── highlight

├── note

├── collection

├── goal

├── statistics

├── notification

├── storage

├── search

├── recommendation

├── common

---

# Standard Feature Layout

Every feature follows the same structure.

feature/

├── controller

├── service

├── repository

├── entity

├── dto

│   ├── request

│   ├── response

│   └── search

├── mapper

├── validation

├── exception

├── specification

├── config

├── event

└── constant

Avoid custom folder layouts.

Consistency is mandatory.

---

# Common Package

app.vaj.common

Contains only reusable infrastructure.

Examples:

config

security

audit

exception

response

logging

event

util

annotation

Never move business logic into common.

---

# Resources

resources/

application.yml

application-dev.yml

application-test.yml

application-prod.yml

db/

messages/

static/

templates/

banner.txt

---

# Database

resources/db/

migration/

seed/

Only Flyway migrations belong in migration.

Seed data belongs in seed.

---

# Configuration

Global configuration

app.vaj.common.config

Feature-specific configuration

app.vaj.<feature>.config

---

# Testing

src/test/java

Mirror production packages exactly.

Example

src/main/java/app/vaj/book/service

↓

src/test/java/app/vaj/book/service

---

# Test Types

unit/

integration/

architecture/

performance/

Fixtures should remain close to tests.

---

# Shared DTOs

Avoid shared DTOs.

Each feature owns its DTOs.

Only truly generic models belong in common.

---

# Static Resources

static/

css/

js/

images/

icons/

fonts/

Keep resources organized.

---

# Templates

templates/

book/

library/

reading/

goal/

layout/

fragment/

Each feature owns its templates.

---

# Build Artifacts

Never commit:

target/

logs/

generated/

temporary files

IDE configuration

OS-specific files

---

# AI Rules

When creating a new feature:

Follow the standard structure.

Do not invent new folder layouts.

Do not place code in unrelated packages.

Prefer consistency over personal preference.