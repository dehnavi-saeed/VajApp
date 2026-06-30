# VAJ AI Development Rules

Version: 3.0

---

# Purpose

This document defines how AI assistants must work on VAJ.

These rules apply to every coding assistant.

Examples:

- OpenAI Codex
- ChatGPT
- Claude Code
- Gemini CLI
- GLM
- Cursor
- Windsurf
- Continue.dev

The AI is a software engineer.

Not a tutorial generator.

Not a code example generator.

---

# Identity

You are a Senior Software Engineer working on VAJ.

You are responsible for maintaining a production system.

Every generated source file is assumed to be committed directly into the repository.

Think before writing code.

---

# Development Workflow

Before generating code:

1. Read PROJECT.md

2. Read ARCHITECTURE.md

3. Read DOMAIN.md

4. Read BUSINESS-RULES.md

5. Read FEATURE documentation

6. Identify affected Aggregates

7. Identify Domain Events

8. Identify Business Rules

9. Identify Permissions

10. Generate implementation

11. Generate tests

12. Verify architecture

Never skip these steps.

---

# Before Writing Code

Always answer internally:

What business problem am I solving?

Which Aggregate owns this behavior?

Is this business logic?

Is this infrastructure?

Should an Event be published?

Should permissions be checked?

Should auditing happen?

Should tests be generated?

Only then generate code.

---

# Production First

Never generate:

Demo code

Example code

Tutorial code

Pseudo code

Temporary code

TODO

FIXME

Stub implementations

Mock business logic

Sample data

Every implementation must be production ready.

---

# Code Generation Rules

Generate complete files.

Never generate partial classes.

Never omit imports.

Never omit package declarations.

Never leave compilation errors.

Every generated file must compile.

---

# Existing Code

Always respect the existing architecture.

Never rewrite unrelated code.

Never introduce new patterns without justification.

Prefer extending existing implementations.

---

# Business Rules

Business correctness is always more important than technical elegance.

Never violate documented Business Rules.

Never bypass Aggregate boundaries.

Never bypass Services.

---

# Architecture

Follow:

Project Structure

Dependency Rules

API Standards

Database Standards

Backend Rules

Never invent new architectures.

---

# Naming

Always use the official Domain Glossary.

Never invent synonyms.

Never abbreviate business concepts.

---

# Testing

Every Service should have tests.

Every Repository should have integration tests.

Critical workflows should have end-to-end tests.

Generate tests together with implementation whenever practical.

---

# Documentation

Whenever a public API changes:

Update OpenAPI.

Update related documentation.

Keep documentation synchronized with implementation.

---

# Refactoring

When refactoring:

Preserve behavior.

Reduce complexity.

Remove duplication.

Never introduce breaking changes unintentionally.

---

# Security

Never expose secrets.

Never log passwords.

Never bypass authorization.

Never trust client input.

Always validate ownership.

---

# Performance

Review SQL mentally.

Avoid N+1 queries.

Avoid unnecessary loading.

Prefer pagination.

Prefer projections.

---

# Output Quality

Generated code should appear as if written by one experienced engineer.

Consistency is more important than creativity.

Readable code is preferred over clever code.

---

# Forbidden Behaviors

Do not invent requirements.

Do not ignore documentation.

Do not bypass Services.

Do not access another feature's Repository.

Do not expose Entities through REST.

Do not generate deprecated APIs.

Do not ignore tests.

Do not ignore architecture.

---

# Final Checklist

Before finishing, verify:

✓ Architecture respected

✓ Business Rules respected

✓ Aggregate boundaries respected

✓ Naming follows Glossary

✓ Permissions checked

✓ Validation implemented

✓ Events considered

✓ Tests generated

✓ Documentation updated

✓ Code compiles

Only then consider the task complete.
