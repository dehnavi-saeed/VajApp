# Reading Goal Feature

Version: 1.0

Complexity
⭐⭐⭐

Priority
High

---

# 1. Purpose

Reading Goals help Users define measurable reading objectives.

Goals motivate continuous learning.

Goals are evaluated automatically.

Users define goals.

The system measures progress.

---

# 2. Vision

Goals transform reading into a habit.

Goals are based on measurable data.

Reading Sessions update Goals automatically.

Goals never modify Reading Sessions.

---

# 3. Scope

Included

• Create Goal

• Update Goal

• Pause Goal

• Resume Goal

• Complete Goal

• Archive Goal

Excluded

• Statistics

• Reading Sessions

• Book Metadata

---

# 4. Responsibilities

Owns

Goal Definition

Target

Period

Status

Progress Snapshot

Does NOT Own

Reading History

Statistics

Books

Highlights

Knowledge Notes

---

# 5. Domain Model

Aggregate Root

ReadingGoal

Value Objects

GoalTarget

GoalProgress

GoalPeriod

Enums

GoalType

DailyPages

DailyMinutes

WeeklyPages

MonthlyBooks

HighlightsCreated

KnowledgeNotesCreated

ReadingStreak

GoalStatus

Draft

Active

Paused

Completed

Cancelled

Archived

---

# 6. Business Rules

Every Goal belongs to one User.

Goals are evaluated automatically.

Users never edit progress directly.

Progress is calculated from system events.

Only one active goal of the same type may exist per User (configurable).

Completed goals become read-only.

Cancelled goals are excluded from statistics.

---

# 7. State Machine

Draft

↓

Active

↓

Paused

↓

Completed

↓

Archived

Cancelled

---

# 8. Lifecycle

Create

Activate

Pause

Resume

Complete

Cancel

Archive

---

# 9. Commands

CreateGoal

UpdateGoal

PauseGoal

ResumeGoal

CancelGoal

ArchiveGoal

---

# 10. Queries

GetGoal

ListGoals

ActiveGoals

CompletedGoals

GoalProgress

GoalHistory

---

# 11. Use Cases

Create Daily Goal

Create Monthly Goal

Pause Goal

Resume Goal

View Progress

Complete Goal

Archive Goal

---

# 12. Domain Events

ReadingGoalCreated

ReadingGoalUpdated

ReadingGoalActivated

ReadingGoalPaused

ReadingGoalCompleted

ReadingGoalCancelled

ReadingGoalArchived

ReadingGoalProgressUpdated

---

# 13. Permissions

ReadingGoal:Read

ReadingGoal:Create

ReadingGoal:Update

ReadingGoal:Delete

ReadingGoal:Archive

---

# 14. REST API

GET    /reading-goals

GET    /reading-goals/{id}

POST   /reading-goals

PATCH  /reading-goals/{id}

POST   /reading-goals/{id}/pause

POST   /reading-goals/{id}/resume

POST   /reading-goals/{id}/cancel

---

# 15. Database

Tables

ReadingGoals

Indexes

UserId

GoalType

Status

StartDate

EndDate

---

# 16. Validation

Goal Type

Required

Target

Must be positive

Start Date

Required

End Date

Must be after Start Date

---

# 17. Search

Fields

Goal Type

Status

Period

Sorting

Start Date

End Date

CreatedAt

---

# 18. Non-Functional Requirements

Goal evaluation <200ms

Asynchronous recalculation

Soft Delete

Audit all changes

Optimistic Locking

---

# 19. AI Rules

Never update Goal progress directly.

Always calculate progress from Reading Sessions and related domain events.

Goals never modify Reading Sessions.

Goals are consumers of business events.

---

# 20. Test Scenarios

Create Goal

Duplicate Active Goal

Pause Goal

Resume Goal

Complete Goal

Cancel Goal

Automatic Progress Update

Goal Expiration

Concurrent Update

---

# 21. Future Evolution

Shared Goals

Library-specific Goals

AI Goal Suggestions

Adaptive Goals

Team Challenges

Habit Tracking