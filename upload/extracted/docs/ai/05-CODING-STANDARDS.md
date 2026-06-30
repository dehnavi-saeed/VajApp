# VAJ Coding Standards

Version: 3.0

---

# Purpose

This document defines the coding standards for the VAJ project.

The objective is not to enforce personal preferences.

The objective is to maximize:

- readability
- maintainability
- consistency
- correctness
- scalability

Every generated source file must follow these standards.

---

# General Principles

Always write production-ready code.

Never generate example code.

Never generate tutorial code.

Never generate pseudo code.

Every generated class must be immediately usable.

---

# Readability First

Code is read far more often than it is written.

Always optimize for readability.

Prefer explicit code.

Avoid clever code.

Avoid surprising behavior.

---

# Consistency

Project consistency is more important than personal preference.

When an existing style exists,
follow it.

Never introduce another style.

The entire codebase should appear to have been written by one experienced engineer.

---

# Simplicity

Choose the simplest implementation that correctly solves the problem.

Avoid unnecessary abstraction.

Avoid unnecessary inheritance.

Avoid unnecessary design patterns.

---

# SOLID

Apply SOLID when it improves the design.

Do not force SOLID mechanically.

Avoid interfaces with only one implementation unless future extensibility is expected.

---

# Package Naming

Use lowercase packages.

Good

app.vaj.book

app.vaj.reading

app.vaj.library

Bad

Book

BookModule

BOOK

book_module

---

# Class Naming

Use nouns.

Good

Book

ReadingSession

BookNote

Highlight

ReadingGoal

Bad

BookManager

BookHelper

BookProcessor

BookImpl

---

# Method Naming

Methods start with verbs.

Good

createBook()

startReading()

finishReading()

createHighlight()

calculateProgress()

archiveBook()

Bad

process()

execute()

run()

handle()

work()

---

# Variable Naming

Use meaningful names.

Good

book

readingSession

highlight

currentProgress

readingDuration

Bad

obj

tmp

data

item

value

Never use single-character variable names except simple loop indexes.

---

# Constants

Constants use UPPER_SNAKE_CASE.

Examples

DEFAULT_PAGE_SIZE

MAX_NOTE_LENGTH

MAX_LIBRARY_NAME_LENGTH

---

# Method Size

Target:

10–30 lines

Review:

50+ lines

Split:

100+ lines

---

# Class Size

Target:

100–300 lines

Review:

500+ lines

Split:

800+ lines

---

# Comments

Explain WHY.

Never explain WHAT.

Good code should explain itself.

---

# JavaDoc

Use JavaDoc only for:

- public APIs
- complex business behavior
- non-obvious decisions

Avoid useless JavaDoc.

---

# Exceptions

Never swallow exceptions.

Never use empty catch blocks.

Throw meaningful exceptions.

Business exceptions belong to the business layer.

---

# Logging

Never log:

Passwords

JWT Tokens

Secrets

Personal sensitive information

Prefer structured logging.

---

# Null Handling

Avoid null whenever possible.

Return empty collections.

Use Optional only as method return values.

Never use Optional in Entities.

Never use Optional in DTOs.

---

# Collections

Program to interfaces.

Use:

List

Map

Set

Avoid exposing mutable collections.

---

# Streams

Use Streams only when they improve readability.

Avoid complex pipelines.

Avoid side effects.

---

# Immutability

Prefer immutable objects.

Use records for immutable DTOs.

Protect mutable state.

---

# Date & Time

Use java.time exclusively.

Never use:

Date

Calendar

Timestamp (legacy)

---

# Money

Always use BigDecimal.

Never use float or double.

Always specify scale and rounding.

---

# Business Logic

Business logic belongs in Services.

Never place business rules in:

Controllers

Repositories

Mappers

DTOs

Entities (except simple domain behavior)

---

# AI Rules

When generating code:

Prefer readability.

Prefer consistency.

Prefer maintainability.

Prefer explicit code.

Avoid unnecessary complexity.

Every file must compile.

Every import must exist.

Every dependency must be valid.

Every implementation must be production-ready.
