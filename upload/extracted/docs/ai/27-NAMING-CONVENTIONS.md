# VAJ Naming Conventions

Version: 1.0

---

# Purpose

Defines the official naming conventions for the VAJ project.

Consistency is mandatory.

Readable code is preferred over short code.

---

# General Rules

Names must represent business concepts.

Avoid abbreviations.

Avoid generic names.

Use ubiquitous language.

---

# Packages

Lowercase.

Singular.

Examples

catalog.book

reading.session

knowledge.note

platform.search

Correct

book

highlight

reading

Incorrect

books

highlights

utils

helpers

---

# Classes

PascalCase

Examples

Book

ReadingSession

KnowledgeNote

Highlight

BookController

ReadingStatistics

---

# Interfaces

No "I" prefix.

Correct

BookRepository

NotificationSender

StorageProvider

Incorrect

IBookRepository

IStorageProvider

---

# Commands

Verb + Noun + Command

Examples

CreateBookCommand

UpdateBookCommand

DeleteBookCommand

StartReadingSessionCommand

FinishReadingSessionCommand

---

# Queries

Verb + Noun + Query

Examples

GetBookQuery

SearchBooksQuery

GetDashboardQuery

SearchKnowledgeQuery

---

# Handlers

CommandName + Handler

Examples

CreateBookHandler

SearchBooksHandler

---

# Controllers

Entity + Controller

Examples

BookController

DashboardController

SearchController

---

# Services

Business Name + Service

Examples

GoalEvaluationService

ReadingStatisticsService

SearchIndexService

---

# Repositories

Entity + Repository

Jpa + Entity + Repository

Examples

BookRepository

JpaBookRepository

---

# Entities

Singular

Examples

Book

Highlight

KnowledgeNote

Bookmark

---

# Value Objects

Meaningful names

Examples

BookTitle

ISBN

ReadingDuration

HighlightColor

EmailAddress

---

# Events

Past Tense

Examples

BookCreated

BookUpdated

ReadingSessionCompleted

HighlightCreated

KnowledgeNotePublished

---

# Exceptions

BusinessName + Exception

Examples

BookAlreadyExistsException

InvalidReadingSessionException

GoalAlreadyCompletedException

---

# DTOs

Request

Response

Examples

CreateBookRequest

BookResponse

UpdateBookRequest

DashboardResponse

---

# REST Endpoints

Plural resources

Examples

/books

/books/{id}

/reading-sessions

/highlights

/knowledge-notes

/search

---

# Database Tables

Plural

Examples

Books

ReadingSessions

KnowledgeNotes

Highlights

Collections

---

# Columns

PascalCase

Examples

BookId

UserId

CreatedAt

UpdatedAt

DeletedAt

ReadingStatus

---

# Variables

camelCase

Examples

bookId

currentPage

readingDuration

knowledgeNote

---

# Constants

UPPER_SNAKE_CASE

Examples

DEFAULT_PAGE_SIZE

MAX_BOOK_TITLE_LENGTH

DEFAULT_LANGUAGE

---

# Enums

Singular

Examples

ReadingStatus

BookFormat

HighlightColor

GoalStatus

---

# Angular Naming

Feature folders

books/

reading/

knowledge/

dashboard/

Component

book-list.component.ts

book-detail.component.ts

reading-session.component.ts

Service

book.service.ts

reading.service.ts

Guard

auth.guard.ts

Resolver

book.resolver.ts

Pipe

reading-time.pipe.ts

---

# Spring Boot Naming

BookController

BookApplicationService

CreateBookHandler

Book

BookRepository

JpaBookRepository

BookEntity

BookMapper

BookCreated

---

# AI Rules

Never invent new naming styles.

Follow this document.

Consistency is more important than personal preference.

When unsure, prefer explicit names.
