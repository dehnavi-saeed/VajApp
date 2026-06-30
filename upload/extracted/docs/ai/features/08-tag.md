# Tag Feature

Version: 1.0

Complexity
⭐⭐

Priority
High

---

# 1. Purpose

Tags provide flexible, user-defined classification.

Tags help users organize and retrieve knowledge.

Unlike Categories, Tags belong to Users.

---

# 2. Vision

Tags represent personal organization.

Every User owns their own Tags.

Tags may be attached to multiple business objects.

---

# 3. Scope

Included

• Create Tag

• Rename Tag

• Delete Tag

• Assign Tag

• Remove Tag

Excluded

• System Categories

• Reading Statistics

---

# 4. Responsibilities

Owns

Tag Metadata

Color

Description

Aliases (Future)

Does NOT Own

Books

Highlights

Notes

Collections

---

# 5. Domain Model

Aggregate Root

Tag

Value Objects

TagName

TagColor

Enums

TagVisibility

Private

Shared (Future)

---

# 6. Business Rules

Every Tag belongs to one User.

Tag names are unique per User.

Different Users may use the same Tag name.

Tags may be assigned to multiple entities.

Deleting a Tag removes only relationships.

Tagged objects remain unchanged.

---

# 7. State Machine

Created

↓

Active

↓

Archived

↓

Deleted

---

# 8. Lifecycle

Create

Rename

Archive

Restore

Delete

---

# 9. Commands

CreateTag

RenameTag

ArchiveTag

RestoreTag

DeleteTag

AssignTag

RemoveTag

MergeTags

---

# 10. Queries

GetTag

ListTags

SearchTags

TagUsage

TaggedObjects

---

# 11. Use Cases

Create Tag

Rename Tag

Assign Tag to Book

Assign Tag to Highlight

Assign Tag to Book Note

Merge Duplicate Tags

Delete Tag

Browse Tagged Content

---

# 12. Domain Events

TagCreated

TagRenamed

TagArchived

TagRestored

TagDeleted

TagAssigned

TagRemoved

TagMerged

---

# 13. Permissions

Tag:Read

Tag:Create

Tag:Update

Tag:Delete

Tag:Assign

---

# 14. REST API

GET /tags

GET /tags/{id}

POST /tags

PATCH /tags/{id}

DELETE /tags/{id}

POST /tags/{id}/assign

POST /tags/{id}/remove

GET /tags/{id}/usage

---

# 15. Database

Tables

Tags

BookTags

HighlightTags

BookNoteTags

Indexes

UserId

Name

Constraints

Unique(UserId, Name)

---

# 16. Validation

Name

Required

2..100

Color

Optional

HEX Color

Description

Optional

500 characters

---

# 17. Search

Fields

Name

Description

Sorting

Name

Usage Count

CreatedAt

---

# 18. Non-Functional Requirements

Search <100ms

Soft Delete

Audit Changes

Optimistic Locking

---

# 19. AI Rules

Tags belong to Users.

Never create system Tags automatically.

Never delete tagged objects.

Tags classify.

They do not own business data.

---

# 20. Test Scenarios

Create Tag

Duplicate Tag

Rename Tag

Merge Tags

Assign Tag

Remove Tag

Delete Tag

Search Tag

Tag Usage

---

# 21. Future Evolution

Nested Tags

Shared Tags

Smart Tags

AI Suggested Tags

Tag Analytics
