# Highlight Feature

Version: 1.0

Complexity
⭐⭐⭐⭐

Priority
Critical

---

# 1. Purpose

Highlights represent meaningful knowledge extracted by the User from a Book.

A Highlight preserves both the selected location and the selected text.

Highlights are first-class knowledge objects.

---

# 2. Vision

Highlights are not visual effects.

Highlights represent knowledge worth remembering.

They become the foundation for notes, reviews, flashcards and AI features.

---

# 3. Scope

Included

• Create Highlight

• Edit Highlight

• Delete Highlight

• Color

• Comment

Excluded

• Book Notes

• Flashcards

• Reading Sessions

---

# 4. Responsibilities

Owns

Selected Text

Position

Color

Comment

CreatedAt

UpdatedAt

Does NOT Own

Book Metadata

Reading

Statistics

---

# 5. Domain Model

Aggregate Root

Highlight

Value Objects

HighlightPosition

TextSnapshot

HighlightColor

Enums

HighlightType

Manual

Imported

AI Generated (Future)

---

# 6. Business Rules

Every Highlight belongs to exactly one Book.

Every Highlight belongs to one User through the Book.

Highlighted text is immutable.

Position must remain valid.

Deleting a Highlight never deletes Notes.

Multiple Highlights may overlap.

Highlights may have Tags.

Highlights may have Comments.

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

Update Color

Update Comment

Archive

Restore

Delete

---

# 9. Commands

CreateHighlight

UpdateHighlight

DeleteHighlight

ArchiveHighlight

RestoreHighlight

AssignTag

RemoveTag

---

# 10. Queries

GetHighlight

ListHighlights

SearchHighlights

HighlightsByBook

HighlightsByTag

RecentHighlights

---

# 11. Use Cases

Highlight Text

Edit Highlight

Delete Highlight

Color Highlight

Browse Highlights

Review Highlights

Search Highlights

---

# 12. Domain Events

HighlightCreated

HighlightUpdated

HighlightDeleted

HighlightArchived

HighlightRestored

HighlightTagged

HighlightCommented

---

# 13. Permissions

Highlight:Read

Highlight:Create

Highlight:Update

Highlight:Delete

Highlight:Archive

---

# 14. REST API

GET /books/{id}/highlights

GET /highlights/{id}

POST /books/{id}/highlights

PATCH /highlights/{id}

DELETE /highlights/{id}

POST /highlights/{id}/tags

DELETE /highlights/{id}/tags/{tagId}

---

# 15. Database

Tables

Highlights

HighlightTags

Indexes

BookId

Page

CreatedAt

Color

---

# 16. Validation

Book required

Page required

Start Position required

End Position required

End >= Start

Snapshot required

Comment

Maximum 2000 characters

---

# 17. Search

Fields

Snapshot

Comment

Tags

Page

Sorting

Page

CreatedAt

Color

---

# 18. Non-Functional Requirements

Highlight creation <100ms

Search <300ms

Soft Delete

Audit Changes

Optimistic Locking

---

# 19. AI Rules

Never modify Book metadata.

Never modify Reading State.

Never modify Reading Sessions.

Always preserve Text Snapshot.

Highlights are immutable knowledge objects.

---

# 20. Test Scenarios

Create Highlight

Invalid Position

Overlapping Highlights

Delete Highlight

Archive Highlight

Tag Highlight

Search Highlight

Concurrent Update

---

# 21. Future Evolution

AI Highlight Suggestions

Highlight Review

Highlight Collections

Flashcard Generation

Knowledge Graph

Linked Highlights
