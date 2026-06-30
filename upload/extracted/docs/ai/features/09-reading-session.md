# Reading Session Feature

Version: 1.0

Complexity
⭐⭐⭐⭐

Priority
Critical

---

# 1. Purpose

Represents a single uninterrupted reading activity.

Reading Sessions are immutable historical records.

They capture how, when and how much a user reads.

A Reading Session is the foundation for progress tracking,
statistics, goals and reading history.

---

# 2. Vision

Every reading activity should be recorded.

Sessions create a permanent history.

Statistics are derived from Sessions.

Reading Sessions never belong directly to Users.

They belong to Books.

---

# 3. Scope

Included

• Start Reading

• Pause Reading

• Resume Reading

• Finish Session

• Cancel Session

Excluded

• Book Metadata

• Notes

• Highlights

• Goals

---

# 4. Responsibilities

Owns

Reading History

Start Time

End Time

Duration

Pages Read

Reading Device (Future)

Location (Optional)

Does NOT Own

Book Metadata

Bookmarks

Statistics

Goals

---

# 5. Domain Model

Aggregate Root

ReadingSession

Value Objects

ReadingDuration

PageRange

ReadingSpeed

Enums

ReadingState

Started

Paused

Completed

Cancelled

---

# 6. Business Rules

Every Reading Session belongs to exactly one Book.

Only one active Reading Session per Book.

StartPage must be less than or equal to EndPage.

Duration must be positive.

Completed Sessions are immutable.

Cancelled Sessions are ignored by statistics.

---

# 7. State Machine

Created

↓

Started

↓

Paused

↓

Resumed

↓

Completed

↓

Archived

Cancelled

---

# 8. Lifecycle

Create

Start

Pause

Resume

Complete

Cancel

Archive

---

# 9. Commands

StartReading

PauseReading

ResumeReading

FinishReading

CancelReading

ArchiveSession

---

# 10. Queries

CurrentSession

ReadingHistory

SessionsByBook

SessionsByDate

LongestSession

AverageSession

---

# 11. Use Cases

Start Reading

Pause Reading

Resume Reading

Finish Reading

View History

Correct Session

---

# 12. Domain Events

ReadingStarted

ReadingPaused

ReadingResumed

ReadingFinished

ReadingCancelled

ReadingArchived

---

# 13. Permissions

Reading:Read

Reading:Create

Reading:Update

Reading:Archive

Reading:Delete

---

# 14. REST API

GET /reading-sessions

GET /reading-sessions/{id}

POST /reading-sessions

POST /reading-sessions/{id}/pause

POST /reading-sessions/{id}/resume

POST /reading-sessions/{id}/finish

POST /reading-sessions/{id}/cancel

---

# 15. Database

Tables

ReadingSessions

Indexes

BookId

StartedAt

CompletedAt

Status

---

# 16. Validation

BookId required

StartPage >= 1

EndPage >= StartPage

Duration > 0

Only one active session per Book

---

# 17. Search

Book

Date

Duration

Status

---

# 18. Non-Functional Requirements

Session creation <100ms

Statistics calculated asynchronously

Soft Delete

Audit all changes

---

# 19. AI Rules

Never update Book metadata.

Never calculate statistics directly.

Never update goals directly.

Only publish Reading events.

---

# 20. Test Scenarios

Start Session

Pause Session

Resume Session

Finish Session

Cancel Session

Duplicate Active Session

Invalid Page Range

Invalid Duration

Concurrent Start

---

# 21. Future Evolution

Pomodoro Integration

Reading Devices

Offline Reading

Multi-device Synchronization

Reading Heatmap
