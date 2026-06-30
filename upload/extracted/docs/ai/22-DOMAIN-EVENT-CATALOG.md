# Domain Event Catalog

Version: 1.0

---

# Purpose

This document defines every official Domain Event in VAJ.

Domain Events describe business facts that have already happened.

Events are immutable.

Past tense naming is mandatory.

Example

BookCreated

ReadingSessionCompleted

HighlightCreated

---

# Event Rules

Every Event

Must be immutable.

Must have EventId.

Must have OccurredAt.

Must have AggregateId.

Must have UserId if applicable.

Must be published after transaction commit.

Never contain business logic.

---

# Standard Metadata

EventId

OccurredAt

AggregateId

AggregateType

UserId

CorrelationId

CausationId

Version

---

# Identity Events

UserRegistered

UserActivated

UserSuspended

UserDeleted

ProfileUpdated

PreferenceUpdated

---

# Library Events

LibraryCreated

LibraryRenamed

LibraryArchived

LibraryRestored

LibraryDeleted

---

# Catalog Events

BookCreated

BookUpdated

BookArchived

BookDeleted

BookRestored

BookCoverChanged

BookMetadataUpdated

AuthorCreated

AuthorUpdated

AuthorMerged

PublisherCreated

PublisherUpdated

CategoryAssigned

CategoryRemoved

---

# Reading Events

ReadingSessionStarted

ReadingSessionPaused

ReadingSessionResumed

ReadingSessionCompleted

ReadingSessionCancelled

BookmarkCreated

BookmarkMoved

BookmarkDeleted

ReadingGoalCreated

ReadingGoalCompleted

ReadingGoalCancelled

---

# Knowledge Events

HighlightCreated

HighlightUpdated

HighlightDeleted

HighlightTagged

KnowledgeNoteCreated

KnowledgeNoteUpdated

KnowledgeNoteDeleted

KnowledgeNotePublished

CollectionCreated

CollectionUpdated

CollectionDeleted

CollectionItemAdded

CollectionItemRemoved

TagCreated

TagUpdated

TagDeleted

---

# Analytics Events

ReadingStateUpdated

StatisticsRebuilt

DashboardUpdated

---

# Platform Events

SearchIndexUpdated

NotificationSent

ExportCompleted

ImportCompleted

StorageUploaded

StorageDeleted

---

# Event Consumers

BookCreated

↓

Search

↓

Dashboard

↓

Statistics

---

ReadingSessionCompleted

↓

ReadingState

↓

ReadingGoal

↓

Statistics

↓

Dashboard

↓

Search

---

HighlightCreated

↓

KnowledgeNote

↓

Search

↓

Dashboard

↓

Statistics

---

KnowledgeNoteCreated

↓

Search

↓

Dashboard

↓

Statistics

---

CollectionUpdated

↓

Search

↓

Dashboard

---

# Event Versioning

Events are append-only.

Breaking changes require new versions.

Example

BookCreated v1

BookCreated v2

Never modify old versions.

---

# Ordering

Ordering is guaranteed only within the same Aggregate.

Cross-aggregate ordering must never be assumed.

---

# Delivery

At least once.

Consumers must be idempotent.

---

# Retry Policy

Retry on temporary failures.

Dead Letter Queue after configurable retries.

---

# AI Rules

Never publish Events before transaction commit.

Never mutate Events.

Never publish integration Events directly from Controllers.

Always publish from the Domain/Application layer.
