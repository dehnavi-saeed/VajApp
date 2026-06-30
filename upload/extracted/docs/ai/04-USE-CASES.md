# VAJ Use Cases

Version: 3.0

---

# Purpose

This document defines the primary user interactions within VAJ.

A Use Case describes what the user wants to accomplish, not how the software is implemented.

Implementation details belong to the Architecture and Backend documents.

---

# Primary Actor

User

---

# UC-001 Register

Goal

Create a new account.

Preconditions

None.

Flow

1. User enters registration information.
2. System validates the input.
3. System creates the account.
4. User receives confirmation.

Postconditions

A new user account exists.

---

# UC-002 Login

Goal

Authenticate the user.

Preconditions

User account exists.

Flow

1. User enters credentials.
2. Credentials are verified.
3. Access token is issued.
4. Refresh token is issued.

Postconditions

Authenticated session is established.

---

# UC-003 Create Library

Goal

Create a personal library.

Flow

1. User selects "New Library".
2. User enters library name.
3. System validates uniqueness.
4. Library is created.

Business Rules

Library names are unique per user.

---

# UC-004 Add Book

Goal

Add a new book.

Flow

1. User selects library.
2. User enters book information.
3. System validates required fields.
4. Book is stored.

Business Rules

Book belongs to exactly one library.

---

# UC-005 Start Reading

Goal

Start reading a book.

Flow

1. User opens a book.
2. User presses Start Reading.
3. System creates a Reading Session.
4. Reading timer begins.

Postconditions

Reading Session becomes active.

---

# UC-006 Pause Reading

Goal

Pause the current session.

Flow

1. User pauses reading.
2. Session duration is updated.
3. Current progress is stored.

---

# UC-007 Finish Reading Session

Goal

Complete a reading session.

Flow

1. User finishes reading.
2. Session is finalized.
3. Reading progress is recalculated.
4. Statistics are updated.

Business Rules

Statistics are generated automatically.

---

# UC-008 Create Highlight

Goal

Highlight important text.

Flow

1. User selects content.
2. User creates highlight.
3. Highlight is saved.

Business Rules

Highlight belongs to one book.

---

# UC-009 Create Note

Goal

Write a personal note.

Flow

1. User opens notes.
2. User writes content.
3. Note is linked to the book.
4. Optional highlight reference is created.

---

# UC-010 Create Bookmark

Goal

Save reading position.

Flow

1. User selects current page.
2. Bookmark is created.

---

# UC-011 Update Reading Progress

Goal

Update reading progress.

Flow

1. Reading Session ends.
2. System recalculates progress.
3. Progress percentage is updated.

Business Rules

Progress cannot exceed 100%.

---

# UC-012 Complete Book

Goal

Mark a book as completed.

Flow

1. Reading reaches final page.
2. System marks book completed.
3. Statistics are updated.
4. Reading streak is updated.

---

# UC-013 Reread Book

Goal

Read a completed book again.

Flow

1. User chooses "Read Again".
2. New Reading Session begins.
3. Previous history is preserved.

Business Rules

Historical reading data must never be deleted.

---

# UC-014 Search Books

Goal

Find books quickly.

Search By

Title

Author

ISBN

Tag

Collection

Publisher

---

# UC-015 Manage Collections

Goal

Organize books.

User may

Create Collection

Rename Collection

Delete Collection

Add Book

Remove Book

Business Rules

Deleting a collection never deletes books.

---

# UC-016 Reading Goal

Goal

Create measurable goals.

Examples

30 pages daily

20 books yearly

Business Rules

Progress is calculated automatically.

---

# UC-017 View Statistics

Goal

View learning analytics.

Examples

Books completed

Pages read

Reading time

Current streak

Monthly progress

Statistics are read-only.

---

# UC-018 Archive Book

Goal

Hide books from active library.

Business Rules

Archive never deletes data.

Archived books remain searchable.

---

# UC-019 Export Notes

Goal

Export personal knowledge.

Supported Formats

Markdown

PDF

JSON

Business Rules

Export never modifies data.

---

# UC-020 Delete Book

Goal

Remove a book.

Business Rules

User confirmation is required.

Reading history should be preserved whenever possible.

Associated notes and highlights follow ownership rules.

---

# AI Rules

When implementing a Use Case:

- Preserve business rules.
- Preserve user knowledge.
- Preserve reading history.
- Prefer explicit workflows.
- Avoid hidden side effects.
