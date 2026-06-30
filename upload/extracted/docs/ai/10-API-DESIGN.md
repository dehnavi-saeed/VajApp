# VAJ API Design Standards

Version: 3.0

---

# Purpose

This document defines the REST API standards for VAJ.

Every API must be:

- Consistent
- Predictable
- Versioned
- Secure
- Easy to understand

API design is part of the product.

Not only part of the backend.

---

# Base URL

/api/v1

Future versions

/api/v2

Never remove old versions without migration strategy.

---

# Resource Naming

Use nouns.

Good

/books

/libraries

/highlights

/notes

/reading-sessions

/goals

Bad

/createBook

/updateBook

/deleteBook

/getBooks

Never expose verbs in URLs.

---

# HTTP Methods

GET

Read resources.

POST

Create resources.

PUT

Replace entire resource.

PATCH

Partial update.

DELETE

Delete resource.

Never use POST for updates unless business requirements demand it.

---

# URL Design

Good

GET /books

GET /books/{id}

POST /books

PATCH /books/{id}

DELETE /books/{id}

GET /books/{id}/highlights

GET /books/{id}/notes

Avoid nested URLs deeper than two levels.

---

# Request Body

Use DTOs.

Never expose JPA Entities.

Separate:

Request DTO

Response DTO

Search DTO

---

# Response Structure

All responses follow the same structure.

Success

{
"success": true,
"data": { ... },
"meta": { ... }
}

Failure

{
"success": false,
"error": {
"code": "...",
"message": "...",
"details": [...]
}
}

Keep responses predictable.

---

# HTTP Status Codes

200 OK

201 Created

204 No Content

400 Bad Request

401 Unauthorized

403 Forbidden

404 Not Found

409 Conflict

422 Unprocessable Entity

500 Internal Server Error

Never return 200 for business failures.

---

# Validation Errors

Validation errors should identify:

Field

Rejected value

Message

Example

{
"field": "title",
"message": "Title is required."
}

---

# Pagination

Collection endpoints support pagination.

Example

GET /books?page=0&size=20

Response meta

page

size

totalItems

totalPages

Never return unlimited collections.

---

# Sorting

Support sorting.

Example

?sort=title

?sort=createdAt,desc

Multiple sorting should be supported.

---

# Filtering

Support explicit filtering.

Examples

?status=completed

?libraryId=...

?authorId=...

Avoid dozens of query parameters.

Use Search DTOs for complex filtering.

---

# Searching

Search should support:

Title

Author

ISBN

Tag

Notes

Highlights

Search APIs must be read-only.

---

# Versioning

Version APIs through the URL.

/api/v1

Avoid versioning individual endpoints.

---

# Idempotency

GET

PUT

DELETE

Must be idempotent.

POST

May create resources.

If duplicate requests are possible, consider Idempotency-Key support.

---

# Security

Every endpoint declares:

Authentication

Authorization

Ownership

Validation

Audit requirement

---

# File Upload

Uploads use multipart/form-data.

Metadata belongs to DTOs.

Large files should support streaming when appropriate.

---

# Date Format

ISO-8601

UTC internally.

Never use localized date strings.

---

# Error Codes

Use stable business error codes.

Example

BOOK_NOT_FOUND

LIBRARY_NOT_FOUND

GOAL_ALREADY_COMPLETED

INVALID_READING_PROGRESS

Error codes should never change without versioning.

---

# API Documentation

Every public endpoint must be documented using OpenAPI.

Examples must remain synchronized with implementation.

---

# AI Rules

When generating an endpoint:

- Use resource-oriented URLs.
- Respect HTTP semantics.
- Never expose entities directly.
- Always use DTOs.
- Keep responses consistent.
- Use the shared error model.
