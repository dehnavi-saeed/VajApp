# VAJ Context Map

Version: 1.0

---

# Purpose

This document defines the official Bounded Contexts of VAJ and the relationships between them.

Every dependency between contexts must be explicitly defined.

No hidden dependencies are allowed.

---

# Bounded Contexts

Identity

Catalog

Reading

Knowledge

Analytics

Platform

Reference Data

---

# High Level Context Map

                +----------------+
                |    Identity    |
                +----------------+
                        |
                        v
                +----------------+
                |    Library      |
                +----------------+
                        |
          +-------------+-------------+
          |                           |
          v                           v

+----------------+ +----------------+
| Catalog | | Reading |
+----------------+ +----------------+
| |
+-------------+-------------+
|
v
+----------------+
| Knowledge |
+----------------+
|
v
+----------------+
| Analytics |
+----------------+
|
v
+----------------+
| Platform |
+----------------+

---

# Context Responsibilities

## Identity

Responsible for:

Authentication

Users

Profiles

Preferences

Never owns Books.

Never owns Reading.

Never owns Knowledge.

---

## Library

Responsible for:

Personal Reading Workspaces

Library Settings

Library Ownership

Never owns Reading Sessions.

Never owns Knowledge.

---

## Catalog

Responsible for:

Books

Authors

Publishers

Categories

Book Metadata

Never owns Reading.

Never owns Notes.

Never owns Statistics.

---

## Reading

Responsible for:

Reading Sessions

Bookmarks

Reading Goals

Reading Timeline

Never owns Books.

Never owns Highlights.

---

## Knowledge

Responsible for:

Highlights

Knowledge Notes

Collections

Tags

Knowledge Organization

Never owns Reading Sessions.

Never owns Statistics.

---

## Analytics

Responsible for:

Reading State

Statistics

Dashboard

Reports

Read Models only.

No business ownership.

---

## Platform

Responsible for:

Search

Notification

Storage

Settings

Import

Export

Infrastructure only.

---

## Reference Data

Responsible for:

Languages

Book Formats

Countries

Themes

System Categories

Read-only domain data.

---

# Allowed Dependencies

Identity

↓

Library

↓

Catalog

↓

Reading

↓

Knowledge

↓

Analytics

↓

Platform

Reference Data may be accessed by every Context.

---

# Forbidden Dependencies

Catalog → Knowledge

Knowledge → Catalog (direct modification)

Analytics → Domain mutations

Platform → Business Logic

Reading → Authentication

---

# Integration Rules

Contexts communicate by:

Domain Events

Queries

Identifiers

Never by sharing Entities.

---

# Shared Identifiers

UserId

LibraryId

BookId

HighlightId

KnowledgeNoteId

CollectionId

ReadingSessionId

---

# Communication Matrix

Identity
-> Library

Library
-> Catalog

Catalog
-> Reading

Reading
-> Knowledge
-> Analytics

Knowledge
-> Analytics

Analytics
-> Dashboard

Platform
-> All Contexts (Infrastructure only)

---

# AI Rules

Never violate Context boundaries.

Never access another Context's database directly.

Communicate through Events, APIs or Queries only.

Never duplicate business logic across Contexts.

---

# Future Contexts

AI

Recommendation

Collaboration

Flashcards

Review Engine

Knowledge Graph
