# VAJ Database Standards

Version: 3.0

---

# Purpose

This document defines the database standards for VAJ.

The database is the single source of truth.

Every schema decision must prioritize:

- Data integrity
- Consistency
- Scalability
- Maintainability
- Performance

Never optimize for short-term convenience.

---

# Database Engine

Primary Database

Microsoft SQL Server 2022+

The application should remain portable whenever practical.

Avoid unnecessary vendor-specific features.

---

# Naming Convention

Database objects use PascalCase.

Examples

User

Library

Book

Author

ReadingSession

BookNote

ReadingGoal

ReadingStatistics

Avoid

tblBook

BOOK

book_table

book_tbl

---

# Primary Keys

Every table must have one immutable primary key.

Preferred type:

uniqueidentifier

Application type:

UUID

Primary keys must never contain business meaning.

Never expose sequential database identifiers.

---

# Business Keys

Business identifiers are separate from primary keys.

Examples

ISBN

Username

Email

LibrarySlug

BookSlug

Business keys require unique constraints when appropriate.

---

# Column Naming

Columns use PascalCase.

Examples

Id

BookId

LibraryId

CreatedAt

UpdatedAt

DeletedAt

Version

Never abbreviate names.

Avoid:

BkId

UsrId

Fld1

Txt

---

# Audit Columns

Every business table should include:

Id

CreatedAt

CreatedBy

UpdatedAt

UpdatedBy

DeletedAt

DeletedBy

IsDeleted

Version

Audit fields should remain identical across all tables.

---

# Data Types

Identifier

uniqueidentifier

Short Text

nvarchar(length)

Long Text

nvarchar(max)

Boolean

bit

Money

decimal(19,4)

Percentage

decimal(5,2)

Date

date

Timestamp

datetime2

Duration

bigint (milliseconds)

Never use float for business data.

---

# String Lengths

Username

100

Book Title

500

Author Name

200

Publisher Name

200

Tag

100

ISBN

20

Email

255

Avoid nvarchar(max) unless necessary.

---

# Foreign Keys

Every relationship must use foreign keys.

Never rely only on application validation.

Database integrity is mandatory.

---

# Indexes

Every foreign key should be indexed.

Create indexes for:

ISBN

Username

Email

CreatedAt

LibraryId

BookId

ReadingSessionId

Design indexes based on query patterns.

Do not over-index.

---

# Unique Constraints

Examples

User.Email

User.Username

Book.ISBN (optional)

Library(UserId, Name)

Tag(UserId, Name)

Unique constraints protect business rules.

---

# Check Constraints

Use check constraints for simple validations.

Examples

ReadingProgress >= 0

ReadingProgress <= 100

ReadingDuration >= 0

PageNumber > 0

GoalTarget > 0

---

# Soft Delete

Business entities use Soft Delete.

Deleted records remain available for:

History

Statistics

Audit

Reporting

Physical deletion is exceptional.

---

# Cascade Delete

Avoid ON DELETE CASCADE by default.

Delete operations must follow business ownership.

Never cascade delete shared entities.

---

# History Preservation

Reading history is permanent.

Notes are permanent.

Highlights are permanent.

Statistics should remain reproducible.

Business history must never be silently lost.

---

# Versioning

Mutable tables use optimistic locking.

Version fields are automatically managed.

Lost updates must be prevented.

---

# Time Strategy

Store timestamps in UTC.

Convert to local time only in presentation.

Never mix time zones.

---

# Migration Strategy

All schema changes must use Flyway.

Never modify production databases manually.

Each migration is immutable.

Never edit executed migrations.

Create a new migration instead.

---

# Performance

Never SELECT \*.

Retrieve only required columns.

Always paginate large result sets.

Design queries for indexes.

Review execution plans for critical queries.

---

# Large Tables

Expected large tables:

ReadingSession

Highlight

BookNote

Statistics

Design with future growth in mind.

---

# AI Rules

When generating database schema:

Prefer explicit constraints.

Prefer readable names.

Protect historical data.

Protect user knowledge.

Never sacrifice integrity for convenience.
