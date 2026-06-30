# VAJ Aggregates

Version: 3.0

---

# Purpose

This document defines the Aggregate boundaries of VAJ.

Aggregates protect business consistency.

Transactions should normally remain inside one Aggregate.

Every Aggregate has exactly one Aggregate Root.

External code communicates only through Aggregate Roots.

---

# Aggregate Principles

Every Aggregate:

- Owns its business rules.
- Protects its invariants.
- Defines transaction boundaries.
- Exposes behavior through the Aggregate Root.
- References other Aggregates by identifier only.

Never share mutable state between Aggregates.

---

# Book Aggregate

Aggregate Root

Book

Children

- ReadingProgress
- Bookmark
- Highlight
- BookNote
- ReadingSession

Responsibilities

- Book metadata
- Reading lifecycle
- Knowledge creation
- Reading history
- Progress tracking

Business Rules

A Book always belongs to one Library.

ReadingProgress belongs to exactly one Book.

Highlights cannot exist without a Book.

BookNotes cannot exist without a Book.

ReadingSessions cannot exist without a Book.

Deleting a Book must never silently delete historical knowledge.

---

# Library Aggregate

Aggregate Root

Library

Children

None

Responsibilities

- Organize books
- Manage ownership

Business Rules

Every Library belongs to one User.

Library names are unique per User.

Deleting a Library never deletes user knowledge automatically.

---

# User Aggregate

Aggregate Root

User

Children

Authentication Preferences

User Settings

Responsibilities

- Identity
- Ownership
- Personal configuration

Business Rules

A User owns Libraries.

A User owns Reading Goals.

A User owns Collections.

---

# Collection Aggregate

Aggregate Root

Collection

Children

Collection Items

Responsibilities

Logical grouping of books.

Collections never own Books.

Books remain independent.

---

# Reading Goal Aggregate

Aggregate Root

ReadingGoal

Children

Goal Progress

Responsibilities

Measure reading objectives.

Business Rules

Progress is calculated automatically.

Goals never modify Reading Sessions.

Reading Sessions update Goals.

---

# Aggregate Communication

Aggregates communicate by identifiers.

Good

BookId

LibraryId

UserId

CollectionId

Avoid direct object references across Aggregates unless required.

---

# Transaction Boundaries

One transaction should normally modify one Aggregate.

Cross-Aggregate workflows should be coordinated by application services.

Avoid long transactions.

---

# Aggregate Invariants

Book

- Always belongs to one Library.
- Cannot exist without an owner.

ReadingSession

- Always belongs to one Book.
- Duration must be positive.

Highlight

- Always references one Book.
- Position must remain valid.

BookNote

- Always belongs to one Book.
- May reference multiple Highlights.

ReadingGoal

- Must have measurable targets.

---

# AI Rules

When implementing business logic:

Always respect Aggregate boundaries.

Never bypass Aggregate Roots.

Never modify child entities directly from Controllers.

Never violate Aggregate invariants.

Prefer consistency over convenience.
