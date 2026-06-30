# VAJ Domain Glossary

Version: 3.0

---

# Purpose

This document defines the official business vocabulary of VAJ.

Every developer, AI assistant, tester, designer and product owner must use these terms consistently.

Never invent synonyms.

Never rename business concepts.

A shared language reduces misunderstandings and improves long-term maintainability.

---

# Core Principles

Every business concept has exactly one official name.

Every official name has exactly one meaning.

Business terminology must remain stable over time.

Technical names must never replace business names.

---

# Core Terms

## User

A person who owns libraries and uses VAJ to build personal knowledge.

A User owns all personal content.

---

## Library

A personal container for organizing books.

A Library belongs to one User.

A User may own multiple Libraries.

Example:

Programming

History

Psychology

---

## Book

A reading resource managed inside a Library.

A Book represents a single intellectual work regardless of reading progress.

A Book is the central business object of VAJ.

---

## Author

The creator of a Book.

Authors may be shared across many Books.

Authors are not owned by Books.

---

## Publisher

The organization responsible for publishing a Book.

Publishers may be shared.

---

## Reading Session

A continuous period of reading activity.

A Reading Session has:

- Start time
- End time
- Duration
- Progress

Reading Sessions are historical records.

---

## Reading Progress

The current state of reading a Book.

Examples:

Current page

Completion percentage

Current status

Reading Progress is calculated from Reading Sessions.

---

## Bookmark

A saved reading position.

Bookmarks help users resume reading later.

Bookmarks are not Reading Progress.

---

## Highlight

A user-selected important passage.

Highlights represent valuable knowledge.

Highlights belong to one Book.

---

## Book Note

A structured piece of user knowledge.

Notes may reference:

Books

Highlights

Other Notes (future)

Book Notes are first-class knowledge objects.

---

## Tag

A user-defined label.

Tags improve organization.

Tags never own data.

---

## Collection

A logical grouping of Books.

Collections are different from Libraries.

Library = ownership.

Collection = organization.

---

## Reading Goal

A measurable objective.

Examples:

Read 20 pages daily.

Finish one book every month.

Goals are evaluated automatically.

---

## Reading Statistics

Automatically generated analytical information.

Examples:

Books completed

Pages read

Reading streak

Average reading time

Statistics are derived data.

They are not manually editable.

---

## Knowledge

The accumulated understanding created by the User.

Knowledge is represented through:

Books

Highlights

Notes

Connections

Knowledge is the most valuable asset in VAJ.

---

## Archive

A state where a Book is hidden from active views without deleting any information.

Archived data remains searchable.

Archived data remains recoverable.

---

## Complete

A Book reaches the end of its reading lifecycle.

Completion never removes historical Reading Sessions.

---

## Re-read

A new reading cycle for a previously completed Book.

Previous history remains preserved.

---

## Ownership

Every business object has exactly one owner.

Ownership defines:

Visibility

Permissions

Responsibility

---

## Soft Delete

Business objects are marked as deleted.

They are not physically removed.

Historical information remains available.

---

## Aggregate

A consistency boundary in the domain model.

External code communicates through Aggregate Roots only.

---

## Domain Event

A business fact that has already happened.

Examples:

BookCreated

ReadingStarted

HighlightCreated

ReadingFinished

Events are immutable.

---

# Forbidden Terms

The following terms must not replace official terminology.

BookNote

❌ Note

❌ Memo

❌ Comment

Reading Session

❌ Log

❌ ReadingLog

❌ SessionLog

Library

❌ Folder

❌ Shelf

❌ Category

Reading Goal

❌ Task

❌ Target

❌ Mission

Book

❌ Product

❌ Item

❌ Resource

---

# Naming Rules

Always use official business names in:

Class names

Method names

API endpoints

Database tables

DTOs

Documentation

Tests

UI labels (where appropriate)

---

# AI Rules

When generating code:

Use only official domain terminology.

Never invent alternative business names.

Never abbreviate business concepts.

If an official term exists, always use it.

Consistency is more important than brevity.
