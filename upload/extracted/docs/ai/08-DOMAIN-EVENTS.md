# VAJ Domain Events

Version: 3.0

---

# Purpose

This document defines the business events of VAJ.

A Domain Event represents something important that has already happened.

Events describe facts.

Events never describe commands.

Good

BookAdded

ReadingStarted

ReadingFinished

HighlightCreated

Bad

CreateBook

StartReading

DeleteBook

Events are immutable.

---

# Event Principles

Every event:

- Represents a completed business action.
- Has occurred in the past.
- Cannot be changed.
- May trigger other actions.

Events must never contain business logic.

---

# Book Events

BookCreated

A new book is added.

BookUpdated

Book metadata changes.

BookArchived

The user archives a book.

BookDeleted

A book is deleted according to business rules.

BookCompleted

The user finishes reading the book.

BookReopened

The user starts reading the book again.

---

# Library Events

LibraryCreated

LibraryRenamed

LibraryDeleted

---

# Reading Events

ReadingStarted

ReadingPaused

ReadingResumed

ReadingFinished

ReadingProgressUpdated

BookmarkCreated

BookmarkRemoved

---

# Knowledge Events

HighlightCreated

HighlightUpdated

HighlightDeleted

BookNoteCreated

BookNoteUpdated

BookNoteDeleted

KnowledgeLinkCreated

KnowledgeLinkRemoved

---

# Goal Events

ReadingGoalCreated

ReadingGoalUpdated

ReadingGoalCompleted

ReadingGoalCancelled

---

# Statistics Events

StatisticsUpdated

ReadingStreakUpdated

BookCounterUpdated

PagesReadUpdated

---

# User Events

UserRegistered

UserActivated

UserDeactivated

UserDeleted

ProfileUpdated

---

# Notification Events

NotificationCreated

NotificationRead

NotificationDismissed

---

# File Events

AttachmentUploaded

AttachmentRemoved

AttachmentReplaced

---

# Event Metadata

Every event should contain:

- EventId
- EventType
- OccurredAt
- AggregateId
- AggregateType
- UserId (if applicable)
- CorrelationId (optional)
- Metadata (optional)

---

# Event Consumers

Events may be consumed by:

Statistics

Notifications

Recommendation Engine

Audit

Search Index

Future Integrations

The publisher must not know who consumes the event.

---

# Event Ordering

Events inside one Aggregate should preserve order.

Events across Aggregates should not assume global ordering.

---

# Idempotency

Event handlers should be idempotent.

Receiving the same event twice must not corrupt business data.

---

# Future Compatibility

The current implementation may execute events synchronously.

The design must allow future asynchronous processing without changing business behavior.

---

# AI Rules

When introducing a new business feature:

- Consider whether it should publish a Domain Event.
- Name events using past tense.
- Never place business logic inside events.
- Events describe facts, not intentions.
