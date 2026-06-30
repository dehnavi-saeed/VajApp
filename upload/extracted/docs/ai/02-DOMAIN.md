# VAJ Domain Model

Version: 3.0

---

# Domain Overview

VAJ is a Reading & Knowledge Management Platform.

The domain is centered around helping users transform books into structured knowledge.

Books are the primary source of knowledge.

Reading activities create learning data.

Learning data becomes long-term knowledge.

---

# Domain Vision

The purpose of the domain is not simply to manage books.

The purpose is to help users:

- Learn continuously
- Organize knowledge
- Remember what they read
- Build personal understanding
- Develop long-term reading habits

Knowledge is the most valuable asset in VAJ.

---

# Core Domain

The Core Domain consists of:

- User
- Library
- Book
- Reading Session
- Reading Progress
- Highlight
- Book Note

Every new feature should strengthen the Core Domain.

---

# Supporting Domains

Supporting domains include:

- Authentication
- Search
- Notifications
- File Storage
- Statistics
- Recommendation
- Settings

Supporting domains exist only to improve the Core Domain.

---

# Main Entities

## User

Represents a person using VAJ.

A User owns one or more Libraries.

---

## Library

Represents a logical collection of books.

Libraries help organize reading by topic, purpose or interest.

Examples:

- Programming
- Philosophy
- Psychology
- History

---

## Book

Represents a single book.

A Book belongs to one Library.

A Book may have:

- Author
- Publisher
- ISBN
- Cover
- Tags

Books are immutable as much as possible.

---

## Author

Represents the creator of one or more books.

Authors are shared across books.

---

## Publisher

Represents the publishing organization.

Publishers are shared across books.

---

## Reading Session

Represents one reading activity.

A Reading Session belongs to exactly one Book.

It records:

- start time
- end time
- pages
- duration

Reading Sessions are append-only.

They should never be edited unless necessary.

---

## Reading Progress

Represents the current reading state of a book.

Examples:

- current page
- percentage
- status

Progress is calculated from Reading Sessions.

---

## Highlight

Represents an important part of a book.

Highlights belong to one Book.

A Highlight may optionally belong to a Reading Session.

---

## Book Note

Represents structured user knowledge.

Notes may reference:

- Book
- Highlight

Notes are first-class knowledge objects.

---

## Bookmark

Represents a saved reading position.

Bookmarks help users continue reading later.

---

## Tag

Represents user-defined categorization.

Tags may be attached to:

- Books
- Notes

---

## Collection

Represents user-defined grouping.

Collections differ from Libraries.

Libraries organize ownership.

Collections organize meaning.

---

## Reading Goal

Represents measurable reading objectives.

Examples:

- Read 30 pages daily
- Finish one book per month
- Read 20 books this year

Goals are measurable.

---

## Reading Statistics

Represents analytical data.

Examples:

- pages read
- hours read
- books completed
- streak
- average reading time

Statistics are generated automatically.

Never edit statistics manually.

---

# Relationships

User

↓

Library

↓

Book

↓

Reading Session

↓

Highlight

↓

Book Note

---

Book

↓

Author

Book

↓

Publisher

Book

↓

Bookmark

Book

↓

Progress

Book

↓

Tags

---

# Aggregate Roots

Aggregate Roots are:

User

Library

Book

Reading Goal

Collections

External code should communicate through Aggregate Roots.

---

# Invariants

A Book always belongs to exactly one Library.

A Reading Session always belongs to one Book.

A Highlight cannot exist without a Book.

A Book Note must belong to a Book.

Statistics are generated.

Statistics are never entered manually.

Knowledge must never be lost.

---

# Ubiquitous Language

Always use these names.

Book

Library

Reading Session

Reading Progress

Highlight

Book Note

Collection

Reading Goal

Reading Statistics

Bookmark

Never invent synonyms.

Never alternate terminology.

---

# AI Rules

When implementing new features:

Always strengthen the Reading experience.

Never introduce ERP concepts.

Never introduce accounting concepts.

Never introduce inventory concepts.

Never introduce unrelated business workflows.

Every new feature should answer:

"How does this improve learning?"
