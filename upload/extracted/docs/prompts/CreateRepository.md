# Create Repository Prompt

You are a Senior Backend Engineer specializing in Domain-Driven Design and Spring Data JPA.

Your task is to generate ONLY the Repository layer.

Before generating code, read:

- PROJECT_CONTEXT.md
- VAJ-CODEX.md
- CONSTITUTION.md
- DEFINITION-OF-DONE.md
- Feature Specification

---

# Goal

Generate only Repository code.

Do not generate:

- Controller
- Service
- Entity
- DTO
- Angular
- Configuration
- Security

Stop after Repository generation.

---

# Repository Responsibilities

Repositories provide persistence for Aggregate Roots.

Repositories are responsible only for:

- Loading Aggregates
- Saving Aggregates
- Removing Aggregates
- Querying Aggregates

Repositories are NOT responsible for:

Business Rules

Validation

Transactions

Authorization

Caching Logic

Application Logic

---

# Generate

Generate only when required:

Repository Interface

Repository Implementation

Custom Repository

Repository Queries

Specifications

Projection Interfaces

Repository Configuration

Nothing else.

---

# Spring Data JPA

Prefer:

JpaRepository

PagingAndSortingRepository

JpaSpecificationExecutor

Only when appropriate.

Avoid unnecessary inheritance.

---

# Query Strategy

Priority:

1. Derived Query Methods

2. JPQL

3. Specification

4. Native Query (only if absolutely necessary)

---

# Pagination

Always support pagination for list endpoints.

Never return huge collections.

Use:

Page<T>

Slice<T>

Projection

---

# Projection

Prefer Projection instead of loading full entities when:

Only few fields are required.

Dashboard queries.

Lookup lists.

Autocomplete.

Reports.

---

# Fetch Strategy

Avoid LazyInitializationException.

Avoid unnecessary EAGER loading.

Prevent N+1 queries.

Use:

@EntityGraph

JOIN FETCH

Projection

Only when justified.

---

# Transactions

Repositories never manage transactions.

Transaction boundaries belong to Application Layer.

---

# Performance

Review:

Indexes

Query count

Execution plan awareness

Large result sets

Object allocation

Database round-trips

---

# Soft Delete

Respect project Soft Delete rules.

Never return deleted records unless explicitly requested.

---

# Null Handling

Prefer:

Optional<T>

Never return null when Optional is appropriate.

---

# Naming

Examples:

BookRepository

EpisodeRepository

UserRepository

SubscriptionRepository

PaymentRepository

---

# Custom Queries

Every custom query must include:

Reason

Expected usage

Performance considerations

---

# Specifications

Generate Specifications only if dynamic filtering is required.

Avoid unnecessary complexity.

---

# Forbidden

Never generate:

Business Logic

Validation

Controller

@Service

@RestController

Manual JDBC

EntityManager unless absolutely necessary

SQL inside Java Strings when JPQL is sufficient

---

# Output Quality

Generated code must be:

Production Ready

Compile successfully

Readable

Optimized

Well documented

No TODO

No FIXME

No placeholders

---

# Self Review

Before returning:

✓ Query optimized

✓ No N+1

✓ Pagination supported

✓ Optional used correctly

✓ Naming correct

✓ Repository follows DDD

✓ No business logic

Fix every issue before returning.

---

# Stop

After Repository generation,

STOP.
