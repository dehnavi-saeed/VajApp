# Book Feature

Version: 1.0

Complexity
⭐⭐⭐

Priority
Critical

---

# 1. Purpose

Represents a book within a Library.

A Book is the primary source of knowledge in VAJ.

Books organize metadata required for reading.

Reading behavior belongs to the Reading feature.

Knowledge belongs to the Knowledge feature.

---

# 2. Vision

Books should remain stable.

Reading history may change.

Knowledge may grow.

Metadata should remain consistent.

Book is the foundation of all learning activities.

---

# 3. Scope

Included

• Book Metadata

• Edition

• Cover

• Language

• Classification

• Reading Status

Excluded

• Reading Sessions

• Reading Progress

• Highlights

• Notes

• Statistics

• Goals

---

# 4. Responsibilities

Owns

Metadata

Book State

Edition Information

Classification

Visibility

Does NOT Own

Reading

Knowledge

Statistics

Authentication

Notifications

---

# 5. Aggregate

Aggregate Root

Book

Entities

BookEdition

BookIdentifier

BookCover

Enums

ReadingStatus

BookFormat

Language

---

# 6. Business Rules

Every Book belongs to one Library.

Every Book belongs to one User through its Library.

Books cannot exist without a Library.

Books are private.

Deleting a Book must never silently remove historical knowledge.

ISBN uniqueness is configurable.

Metadata updates never reset reading progress.

---

# 7. State Machine

Draft

↓

Unread

↓

Reading

↓

Completed

↓

Archived

↓

Deleted

ReReading starts a new Reading lifecycle.

---

# 8. Lifecycle

Create

Update

Archive

Restore

Delete

Start ReReading

---

# 9. Commands

CreateBook

UpdateBook

ArchiveBook

RestoreBook

DeleteBook

DuplicateBook

ChangeCover

UpdateMetadata

---

# 10. Queries

GetBook

ListBooks

SearchBooks

RecentlyAddedBooks

RecentlyReadBooks

ArchivedBooks

---

# 11. Use Cases

Create Book

Edit Metadata

Archive Book

Restore Book

Delete Book

Search Book

View Book Details

Duplicate Book

---

# 12. Domain Events

BookCreated

BookUpdated

BookArchived

BookRestored

BookDeleted

BookCoverChanged

BookDuplicated

---

# 13. Permissions

Book:Create

Book:Read

Book:Update

Book:Delete

Book:Archive

Book:Restore

Book:Export

---

# 14. REST API

GET /books

GET /books/{id}

POST /books

PATCH /books/{id}

DELETE /books/{id}

POST /books/{id}/archive

POST /books/{id}/restore

POST /books/{id}/duplicate

POST /books/{id}/cover

---

# 15. Database

Tables

Books

BookIdentifiers

BookEditions

Indexes

LibraryId

ISBN

Title

Status

Constraints

(LibraryId, Title)

---

# 16. Validation

Title

Required

3..500

ISBN

Optional

Valid format

Language

Required

Format

Valid enum

Page Count

Positive integer

---

# 17. Search

Search Fields

Title

Subtitle

Author

Publisher

ISBN

Tags

Language

Filters

Status

Library

Language

Publisher

Sorting

Title

CreatedAt

UpdatedAt

---

# 18. Non-Functional Requirements

Book search <300ms

Book details <200ms

Optimistic Locking

Soft Delete

Audit metadata changes

---

# 19. AI Rules

Never place Reading logic inside Book.

Never place Highlight logic inside Book.

Never calculate Statistics inside Book.

Book owns metadata only.

---

# 20. Test Scenarios

Create Book

Duplicate ISBN

Archive Book

Restore Book

Delete Book

Change Cover

Update Metadata

Invalid Language

Invalid ISBN

Concurrent Update

---

# 21. Future Evolution

Multiple Editions

External Metadata Providers

ISBN Lookup

Book Import

AI Metadata Completion

Multi-language Metadata
