# Author Feature

Version: 1.0

Complexity
⭐

Priority
High

---

# 1. Purpose

Represents a person or organization responsible for creating one or more books.

Authors are reusable catalog entities.

An Author contains descriptive information only.

Reading activities are unrelated to Authors.

---

# 2. Vision

Author information should be shared across books.

Updating an Author automatically enriches every related Book.

Authors never own Books.

Books reference Authors.

---

# 3. Scope

Included

• Create Author

• Update Author

• Search Authors

• View Author

Excluded

• Books

• Reading

• Statistics

• Notes

---

# 4. Responsibilities

Owns

Author Metadata

Biography

Aliases

External References

Does NOT Own

Books

Reading

Knowledge

Goals

---

# 5. Domain Model

Aggregate Root

Author

Value Objects

AuthorName

Biography

ExternalReference

Enums

AuthorType

Person

Organization

Unknown

---

# 6. Business Rules

An Author may be linked to many Books.

Books may have multiple Authors.

Authors can exist without Books.

Deleting an Author must not delete Books.

Author names are unique where possible.

Aliases may exist.

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

Update

Archive

Restore

Delete

---

# 9. Commands

CreateAuthor

UpdateAuthor

ArchiveAuthor

RestoreAuthor

DeleteAuthor

MergeAuthors

---

# 10. Queries

GetAuthor

ListAuthors

SearchAuthors

AuthorBooks

---

# 11. Use Cases

Create Author

Edit Biography

Merge Duplicate Authors

Archive Author

Search Author

View Books by Author

---

# 12. Domain Events

AuthorCreated

AuthorUpdated

AuthorArchived

AuthorMerged

AuthorDeleted

---

# 13. Permissions

Author:Read

Author:Create

Author:Update

Author:Archive

Author:Delete

---

# 14. REST API

GET /authors

GET /authors/{id}

POST /authors

PATCH /authors/{id}

DELETE /authors/{id}

GET /authors/{id}/books

---

# 15. Database

Tables

Authors

BookAuthors

Indexes

Name

Slug

Constraints

Unique(Name)

---

# 16. Validation

Name

Required

2..200

Biography

Optional

Maximum Length

5000

---

# 17. Search

Fields

Name

Alias

Biography

Sorting

Name

CreatedAt

---

# 18. Non-Functional Requirements

Search <200ms

Soft Delete

Audit Changes

Optimistic Locking

---

# 19. AI Rules

Never place Books inside Author.

Never place Reading logic inside Author.

Never calculate statistics.

Author contains metadata only.

---

# 20. Test Scenarios

Create Author

Duplicate Name

Merge Authors

Archive Author

Restore Author

Delete Author

Search Author

---

# 21. Future Evolution

Wikipedia Integration

ORCID

OpenLibrary

Multiple Aliases

Localized Biography
