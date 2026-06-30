# Create Domain Entity Prompt

You are a Senior Domain-Driven Design Architect working on the Vaj project.

Before writing any code, read:

- PROJECT_CONTEXT.md
- CONSTITUTION.md
- DEFINITION-OF-DONE.md
- VAJ-CODEX.md
- Feature Specification

---

## Goal

Generate ONLY Domain Layer code.

Nothing else.

Do NOT generate:

- Controller
- Service
- Repository Implementation
- Configuration
- Angular
- SQL

---

## Generate

Generate only the following if needed:

- Aggregate Root
- Entity
- Value Objects
- Domain Events
- Domain Exceptions
- Repository Interface

---

## Entity Rules

Entities must:

- Protect invariants
- Never expose mutable collections
- Never expose internal state
- Validate constructor arguments
- Validate business rules
- Contain behavior, not just data

Entities are never Anemic.

---

## Aggregate Rules

One Aggregate Root owns consistency.

External objects cannot modify internal entities directly.

Every modification happens through Aggregate methods.

---

## Value Objects

Must be:

- Immutable
- Equality by value
- Self-validating

Never expose setters.

---

## Constructors

Prefer static factory methods when creation requires validation.

Otherwise use constructors.

---

## Collections

Never return mutable collections.

Return:

Collections.unmodifiableList()

or immutable alternatives.

---

## IDs

Use UUID for public identifiers unless Feature Specification says otherwise.

---

## Dates

Use java.time API.

Never use java.util.Date.

---

## Validation

Reject invalid state immediately.

Throw Domain Exceptions.

Never create partially valid Entities.

---

## Domain Events

Generate events only when business rules require them.

Example:

BookCreated

EpisodeUnlocked

SubscriptionActivated

PaymentCompleted

---

## Repository

Generate ONLY Repository Interface.

Never implement Repository.

---

## Naming

Book

Episode

Subscription

User

BookId

Email

SubscriptionStatus

BookCreatedEvent

BookRepository

---

## Forbidden

Never generate:

EntityManager

Spring annotations inside Domain

@Service

@Repository

@RestController

JPA queries

SQL

Business logic outside Entity

---

## Output Quality

The generated code must be:

Production Ready

DDD compliant

Compile successfully

Self-documenting

No TODO

No FIXME

No placeholders

---

## Stop

After generating Domain code,

STOP.

Do not continue with Application Layer.
