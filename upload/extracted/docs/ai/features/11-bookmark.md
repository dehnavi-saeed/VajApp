# Bookmark Feature

Version: 1.0

Complexity
⭐⭐

Priority
High

---

# 1. Purpose

Bookmarks represent intentional return points created by the User.

Bookmarks help Users quickly return to meaningful locations inside a Book.

Bookmarks are independent from Reading State.

The last reading position is managed by Reading State.

---

# 2. Vision

Bookmarks preserve meaningful locations.

A Book may contain multiple Bookmarks.

Bookmarks improve navigation and review.

---

# 3. Scope

Included

• Create Bookmark

• Update Bookmark

• Delete Bookmark

• Reorder Bookmarks

• Search Bookmarks

Excluded

• Reading Progress

• Reading Sessions

• Highlights

---

# 4. Responsibilities

Owns

Page

Chapter (Optional)

Title

Description

Color

Order

CreatedAt

Does NOT Own

Reading Progress

Statistics

Highlights

Book Metadata

---

# 5. Domain Model

Aggregate Root

Bookmark

Value Objects

BookmarkPosition

BookmarkTitle

BookmarkColor

Enums

BookmarkType

Manual

Automatic (Future)

---

# 6. Business Rules

Every Bookmark belongs to exactly one Book.

A Book may contain many Bookmarks.

Bookmarks never change Reading Progress.

Deleting a Bookmark never changes Reading History.

Bookmark page must exist within the Book.

Titles are optional.

Users may reorder Bookmarks.

---

# 7. State Machine

Created

↓

Updated

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

CreateBookmark

UpdateBookmark

MoveBookmark

DeleteBookmark

ArchiveBookmark

RestoreBookmark

---

# 10. Queries

GetBookmark

ListBookmarks

SearchBookmarks

BookmarksByBook

RecentBookmarks

---

# 11. Use Cases

Create Bookmark

Rename Bookmark

Change Bookmark Color

Move Bookmark

Delete Bookmark

Jump to Bookmark

View Bookmarks

---

# 12. Domain Events

BookmarkCreated

BookmarkUpdated

BookmarkMoved

BookmarkDeleted

BookmarkArchived

BookmarkRestored

---

# 13. Permissions

Bookmark:Read

Bookmark:Create

Bookmark:Update

Bookmark:Delete

Bookmark:Archive

---

# 14. REST API

GET /books/{id}/bookmarks

GET /bookmarks/{id}

POST /books/{id}/bookmarks

PATCH /bookmarks/{id}

DELETE /bookmarks/{id}

POST /bookmarks/{id}/move

---

# 15. Database

Tables

Bookmarks

Indexes

BookId

Page

Order

CreatedAt

---

# 16. Validation

Page

Required

Must be between 1 and Book.PageCount

Title

Optional

Maximum 200 characters

Description

Optional

Maximum 1000 characters

Color

Optional

Valid predefined color

---

# 17. Search

Fields

Title

Description

Page

Sorting

Page

CreatedAt

Order

---

# 18. Non-Functional Requirements

Bookmark creation <100ms

Soft Delete

Audit Changes

Optimistic Locking

---

# 19. AI Rules

Bookmarks never update Reading State.

Bookmarks never modify Reading Sessions.

Bookmarks are navigation helpers.

Do not confuse Bookmarks with Highlights.

---

# 20. Test Scenarios

Create Bookmark

Duplicate Bookmark

Move Bookmark

Delete Bookmark

Invalid Page

Reorder Bookmarks

Search Bookmarks

Concurrent Update

---

# 21. Future Evolution

Bookmark Folders

Pinned Bookmarks

Favorite Bookmarks

Bookmark Sharing

AI Suggested Bookmarks
