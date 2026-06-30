# Statistics Feature

Version: 1.0

Complexity
⭐⭐⭐⭐

Priority
High

---

# 1. Purpose

Statistics provide analytical insights about the User's reading habits and knowledge growth.

Statistics are read models.

They never own business data.

All values are derived from domain events and projections.

---

# 2. Vision

Statistics transform raw activity into meaningful insights.

Users should understand:

How much they read.

How consistently they read.

How knowledge grows.

Statistics never modify domain objects.

---

# 3. Scope

Included

• Reading Statistics

• Library Statistics

• Book Statistics

• Goal Statistics

• Knowledge Statistics

Excluded

• Reading Sessions

• Reading Goals

• Books

• Highlights

---

# 4. Responsibilities

Provides

Reading Time

Pages Read

Books Finished

Current Streak

Average Reading Speed

Goal Completion

Knowledge Growth

Does NOT Own

Reading Sessions

Books

Highlights

Knowledge Notes

Goals

---

# 5. Domain Model

Projection

ReadingStatistics

LibraryStatistics

BookStatistics

KnowledgeStatistics

GoalStatistics

---

# 6. Business Rules

Statistics are calculated.

Statistics are eventually consistent.

Users cannot edit statistics.

Statistics may be rebuilt at any time.

Cancelled Reading Sessions are excluded.

Archived data remains included unless configured otherwise.

---

# 7. Data Sources

Reading Sessions

Reading State

Reading Goals

Highlights

Knowledge Notes

---

# 8. Queries

Overall Statistics

Book Statistics

Library Statistics

Daily Statistics

Weekly Statistics

Monthly Statistics

Yearly Statistics

Knowledge Statistics

---

# 9. Metrics

Books Started

Books Completed

Pages Read

Reading Time

Average Session

Average Reading Speed

Current Streak

Longest Streak

Highlights Created

Knowledge Notes Created

Collections Created

Goals Completed

---

# 10. REST API

GET /statistics

GET /statistics/books

GET /statistics/libraries

GET /statistics/reading

GET /statistics/goals

GET /statistics/knowledge

GET /statistics/history

---

# 11. Database

Projection Tables

ReadingStatistics

LibraryStatistics

BookStatistics

GoalStatistics

KnowledgeStatistics

Rebuildable

---

# 12. Validation

Read-only.

No direct updates.

---

# 13. Search

Not Applicable

---

# 14. Non-Functional Requirements

Response <300ms

Async updates

Projection rebuild

Cacheable

---

# 15. AI Rules

Never update Statistics directly.

Statistics consume events only.

Statistics never publish business events.

---

# 16. Test Scenarios

Projection rebuild

Cancelled Session

Goal Completion

Large Dataset

Concurrent Updates

Projection Recovery

---

# 17. Future Evolution

Heatmap

Reading Trends

Predictive Analytics

AI Reading Insights

Weekly Digest
