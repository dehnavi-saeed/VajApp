# Dashboard Feature

Version: 1.0

Complexity
⭐⭐⭐

Priority
High

---

# 1. Purpose

Dashboard provides a unified overview of the User's reading activity,
knowledge growth and learning progress.

Dashboard is a read model.

It aggregates information from multiple projections.

---

# 2. Vision

Dashboard answers one question:

"What is happening in my learning journey?"

Dashboard provides actionable insights.

Dashboard never owns business data.

---

# 3. Scope

Included

• Reading Summary

• Current Books

• Active Goals

• Reading Streak

• Recent Highlights

• Recent Knowledge Notes

• Collections

Excluded

• Editing Data

• Book Management

• Reading Sessions

• Statistics Calculation

---

# 4. Responsibilities

Displays

Current Reading

Reading Goals

Recent Activity

Knowledge Growth

Collections

Reading Trends

Does NOT Own

Books

Goals

Reading Sessions

Knowledge Notes

Highlights

---

# 5. Data Sources

ReadingState

Statistics

ReadingGoal

Highlight

KnowledgeNote

Collection

Library

---

# 6. Widgets

Current Reading

Continue Reading

Today's Goal

Reading Streak

Weekly Reading

Monthly Reading

Recent Highlights

Recent Notes

Knowledge Growth

Collections

Recently Added Books

---

# 7. Queries

Get Dashboard

Reading Summary

Knowledge Summary

Current Goal

Recent Activity

---

# 8. REST API

GET /dashboard

GET /dashboard/reading

GET /dashboard/knowledge

GET /dashboard/goals

GET /dashboard/activity

---

# 9. Database

Projection

DashboardSnapshot (Optional)

Cache

---

# 10. Validation

Read-only.

No direct updates.

---

# 11. Performance

Dashboard <500ms

Cached

Projection-based

Lazy loading for widgets

---

# 12. AI Rules

Never update Dashboard directly.

Dashboard aggregates existing data.

Dashboard never publishes domain events.

---

# 13. Test Scenarios

Dashboard Load

Large Dataset

Empty Dashboard

Cache Rebuild

Projection Rebuild

Widget Loading

---

# 14. Future Evolution

Custom Widgets

Drag & Drop Dashboard

Library-specific Dashboard

AI Dashboard

Learning Insights
