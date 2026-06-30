# Knowledge Note Feature

Version: 1.0

Complexity
⭐⭐⭐⭐⭐

Priority
Critical

---

# 1. Purpose

A Knowledge Note represents a user's understanding, insight or interpretation of one or more knowledge sources.

Knowledge Notes are the core knowledge objects in VAJ.

Unlike Highlights, which preserve extracted text, Knowledge Notes capture original thinking.

---

# 2. Vision

Knowledge Notes transform information into knowledge.

A Highlight answers:

"What was important?"

A Knowledge Note answers:

"What did I understand?"

Knowledge Notes are permanent intellectual assets.

---

# 3. Scope

Included

• Create Note

• Edit Note

• Rich Text

• Markdown

• Links

• References

• Tags

Excluded

• Reading Sessions

• Reading Progress

• Statistics

---

# 4. Responsibilities

Owns

Title

Content

References

Tags

CreatedAt

UpdatedAt

Attachments (Future)

Does NOT Own

Book Metadata

Highlights

Reading History

Statistics

---

# 5. Domain Model

Aggregate Root

KnowledgeNote

Value Objects

NoteTitle

NoteContent

Reference

Enums

NoteStatus

Draft

Published

Archived

Deleted

---

# 6. Business Rules

Every Knowledge Note belongs to one User.

Knowledge Notes may reference multiple Books.

Knowledge Notes may reference multiple Highlights.

Knowledge Notes may exist without Highlights.

Deleting a Highlight never deletes a Knowledge Note.

Deleting a Book never deletes a Knowledge Note.

Knowledge must survive.

---

# 7. State Machine

Draft

↓

Published

↓

Archived

↓

Deleted

Draft

↓

Deleted

---

# 8. Lifecycle

Create

Edit

Publish

Archive

Restore

Delete

---

# 9. Commands

CreateKnowledgeNote

UpdateKnowledgeNote

PublishKnowledgeNote

ArchiveKnowledgeNote

RestoreKnowledgeNote

DeleteKnowledgeNote

AttachHighlight

DetachHighlight

AttachBook

DetachBook

AssignTag

RemoveTag

---

# 10. Queries

GetKnowledgeNote

SearchKnowledgeNotes

NotesByBook

NotesByHighlight

RecentNotes

FavoriteNotes (Future)

---

# 11. Use Cases

Create Note

Edit Note

Link Highlights

Link Books

Search Notes

Review Notes

Archive Note

---

# 12. Domain Events

KnowledgeNoteCreated

KnowledgeNoteUpdated

KnowledgeNotePublished

KnowledgeNoteArchived

KnowledgeNoteDeleted

HighlightAttached

HighlightDetached

BookAttached

BookDetached

---

# 13. Permissions

KnowledgeNote:Read

KnowledgeNote:Create

KnowledgeNote:Update

KnowledgeNote:Delete

KnowledgeNote:Publish

KnowledgeNote:Archive

---

# 14. REST API

GET /knowledge-notes

GET /knowledge-notes/{id}

POST /knowledge-notes

PATCH /knowledge-notes/{id}

DELETE /knowledge-notes/{id}

POST /knowledge-notes/{id}/highlights

DELETE /knowledge-notes/{id}/highlights/{highlightId}

POST /knowledge-notes/{id}/books

DELETE /knowledge-notes/{id}/books/{bookId}

---

# 15. Database

Tables

KnowledgeNotes

KnowledgeNoteBooks

KnowledgeNoteHighlights

KnowledgeNoteTags

Indexes

UserId

CreatedAt

UpdatedAt

Status

---

# 16. Validation

Title

Optional

Maximum 300 characters

Content

Required

Markdown Supported

Maximum configurable

At least one of:

Book

Highlight

Standalone Note

---

# 17. Search

Fields

Title

Content

Tags

Books

Highlights

Sorting

UpdatedAt

CreatedAt

Title

---

# 18. Non-Functional Requirements

Autosave

Optimistic Locking

Version History (Future)

Full-text Search

Audit Changes

Soft Delete

---

# 19. AI Rules

Knowledge Notes represent user thinking.

Never overwrite user-written content.

Never remove references automatically.

Preserve links to Books and Highlights.

Knowledge Notes are independent from Books.

---

# 20. Test Scenarios

Create Note

Update Note

Attach Highlight

Detach Highlight

Attach Book

Delete Highlight

Delete Book

Search Note

Concurrent Update

Autosave

---

# 21. Future Evolution

Backlinks

Knowledge Graph

AI Summary

Flashcard Generation

Spaced Repetition

Obsidian Export

Notion Export
