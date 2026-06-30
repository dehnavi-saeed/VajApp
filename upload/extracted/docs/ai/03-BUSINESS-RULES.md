# VAJ Business Rules

Version: 3.0

---

# Purpose

This document defines the business rules of VAJ.

Business rules are independent of technology.

They must remain valid regardless of:

- Programming language
- Database
- Framework
- UI
- API

Business rules describe how VAJ behaves.

---

# General Principles

Knowledge is the primary asset.

Books are the source of knowledge.

Reading creates learning.

Learning creates knowledge.

Knowledge should never be lost.

---

# User Rules

A user must authenticate before accessing personal data.

Each user owns their own data.

Users cannot access another user's libraries.

Deleting a user must never accidentally delete shared entities such as Authors or Publishers.

---

# Library Rules

Every library belongs to exactly one user.

A library may contain many books.

A book belongs to exactly one library.

Library names must be unique per user.

Deleting a library is only allowed when business rules permit.

---

# Book Rules

Every book belongs to one library.

A book may have one or more authors.

A book may have one publisher.

A book may have multiple tags.

Books are never shared between users.

Book metadata should remain stable.

Changing metadata must never delete reading history.

---

# Reading Rules

A reading session always belongs to one book.

Reading sessions are historical records.

Reading history should not be deleted.

Reading duration must always be positive.

Reading progress cannot exceed 100%.

Progress cannot become negative.

A completed book remains completed unless the user explicitly starts rereading.

---

# Highlight Rules

A highlight belongs to one book.

Highlights represent important knowledge.

Deleting a highlight requires user confirmation.

Highlights should preserve their original position inside the book.

---

# Book Note Rules

A note belongs to one book.

A note may reference one or more highlights.

Notes represent user knowledge.

Notes must never be automatically deleted.

---

# Bookmark Rules

Bookmarks belong to one book.

Multiple bookmarks are allowed.

Bookmarks should preserve reading position.

---

# Tag Rules

Users create their own tags.

Tags may be reused.

Deleting a tag must not delete books.

Deleting a tag only removes the association.

---

# Collection Rules

Collections organize books.

Books may belong to multiple collections.

Deleting a collection never deletes books.

---

# Reading Goal Rules

Goals must be measurable.

Goals must have a target.

Goals may be:

Daily

Weekly

Monthly

Yearly

Goal progress is calculated automatically.

Users cannot manually modify calculated progress.

---

# Statistics Rules

Statistics are generated.

Statistics are read-only.

Statistics are derived from reading history.

Statistics must never be manually edited.

---

# Search Rules

Search never changes data.

Search must support partial matching.

Search results should prioritize relevance.

---

# Notification Rules

Notifications must never interrupt reading.

Notifications should be actionable.

Users may disable notifications.

---

# Attachment Rules

Attachments belong to one entity.

Deleting an entity should follow ownership rules.

Large files should not block business operations.

---

# Data Integrity Rules

Knowledge must never be lost.

Reading history must never be silently modified.

Business data must remain consistent.

Soft Delete is preferred for important records.

---

# Future Rules

Future features must support:

Learning

Reading

Knowledge organization

Long-term memory

Do not introduce workflows unrelated to these goals.

---

# AI Decision Rules

When multiple implementations are possible:

Prefer the one that protects user knowledge.

Prefer the one that preserves history.

Prefer the one that improves learning.

Never sacrifice data integrity for convenience.

Business correctness is always more important than implementation simplicity.
