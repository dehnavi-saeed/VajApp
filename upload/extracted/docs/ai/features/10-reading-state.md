# Reading State Feature

Version: 1.0

Complexity
⭐⭐⭐

Priority
Critical

---

# 1. Purpose

Reading State is a read model that represents the current reading status of a Book.

It is derived from Reading Sessions.

Users never edit Reading State directly.

---

# 2. Vision

Reading State provides fast access to the current reading position.

Historical information always comes from Reading Sessions.

Reading State may be rebuilt at any time.

---

# 3. Scope

Included

• Current Page

• Reading Percentage

• Current Status

• Last Reading Date

• Estimated Finish

Excluded

• Reading History

• Statistics

• Notes

---

# 4. Responsibilities

Provides

Current Reading Snapshot

Current Position

Current Progress

Current Reading Status

Does NOT Own

Reading History

Book Metadata

Bookmarks

Statistics

---

# 5. Business Rules

Reading State is calculated.

Users cannot edit Reading State directly.

Reading State is updated after every completed Session.

Reading State may be rebuilt from history.

---

# 6. Data Model

Current Page

Progress Percentage

Current Status

Last Session

Total Reading Time

Total Pages Read

Current Speed

Estimated Finish

---

# 7. Commands

None

Read Model only.

---

# 8. Queries

Get Reading State

Get Current Progress

Get Estimated Finish

---

# 9. Events

ReadingStateUpdated

---

# 10. REST API

GET /books/{id}/reading-state

---

# 11. Database

ReadingStates

Projection Table

May be regenerated.

---

# 12. Validation

No manual updates.

Only calculated values.

---

# 13. AI Rules

Never update Reading State directly.

Always update Reading Session.

Projection updates automatically.

---

# 14. Test Scenarios

Projection Rebuild

Projection Update

Multiple Sessions

Cancelled Session

Deleted Session
