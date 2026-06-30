# VAJ Policies

Version: 1.0

---

# Purpose

Policies define how the system reacts to Domain Events.

Policies coordinate business workflows.

Policies never contain UI logic.

Policies never expose APIs.

Policies react to Events and trigger Commands.

---

# General Rules

Policies listen to Domain Events.

Policies may execute one or more Commands.

Policies never modify Aggregates directly.

Policies must be idempotent.

Policies may fail independently.

Policies must be retryable.

Policies should be asynchronous unless immediate consistency is required.

---

# Identity Policies

## UserRegistered

Actions

Create Default Library

Create Default Settings

Initialize User Dashboard

Initialize Statistics Projection

Initialize Search Index

---

# Library Policies

## LibraryCreated

Actions

Initialize Library Statistics

Initialize Library Dashboard

Initialize Search Projection

---

## LibraryDeleted

Actions

Remove Search Index

Archive Related Projections

Never delete Books automatically

---

# Catalog Policies

## BookCreated

Actions

Create Reading State

Initialize Statistics

Update Search Index

Update Dashboard

---

## BookUpdated

Actions

Refresh Search Index

Refresh Dashboard

---

## BookDeleted

Actions

Archive Reading State

Remove Search Index

Refresh Dashboard

---

# Reading Policies

## ReadingSessionStarted

Actions

Update Reading State

Refresh Dashboard

---

## ReadingSessionPaused

Actions

Update Reading State

---

## ReadingSessionCompleted

Actions

Update Reading State

Evaluate Reading Goals

Update Statistics

Refresh Dashboard

Update Search Ranking

---

## BookmarkCreated

Actions

Refresh Dashboard

---

## ReadingGoalCompleted

Actions

Update Statistics

Refresh Dashboard

Create Achievement (Future)

Notify User (Optional)

---

# Knowledge Policies

## HighlightCreated

Actions

Update Search Index

Refresh Dashboard

Update Knowledge Statistics

Evaluate Review Candidates (Future)

---

## HighlightDeleted

Actions

Update Search Index

Refresh Dashboard

---

## KnowledgeNoteCreated

Actions

Update Search Index

Refresh Dashboard

Update Knowledge Statistics

Generate Backlinks (Future)

---

## KnowledgeNoteUpdated

Actions

Refresh Search Index

Refresh Dashboard

---

## CollectionUpdated

Actions

Refresh Search Index

Refresh Dashboard

---

# Analytics Policies

## StatisticsProjectionUpdated

Actions

Refresh Dashboard

---

## ReadingStateUpdated

Actions

Refresh Dashboard

Evaluate Reading Goals

---

# Platform Policies

## SearchIndexUpdated

Actions

Invalidate Search Cache

---

## ExportCompleted

Actions

Notify User

Write Audit Log

---

## ImportCompleted

Actions

Rebuild Search Index

Refresh Dashboard

Rebuild Statistics

---

# Failure Rules

Policy execution failures must never rollback Domain transactions.

Failures are retried automatically.

Repeated failures are sent to Dead Letter Queue.

---

# Ordering Rules

Policies must not assume global event ordering.

Ordering is guaranteed only inside one Aggregate.

---

# Idempotency

Every Policy must safely process duplicate Events.

Repeated execution must produce the same final state.

---

# AI Rules

Never place business logic inside Controllers.

Never update Aggregates directly from Policies.

Always trigger Commands.

Never call another Aggregate directly.

Policies orchestrate.

Aggregates decide.
