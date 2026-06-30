# VAJ Authorization Model

Version: 3.0

---

# Purpose

This document defines the authorization model of VAJ.

Authentication answers:

Who are you?

Authorization answers:

What are you allowed to do?

---

# Principles

Authorization is permission-based.

Roles are collections of permissions.

Business logic never depends directly on roles.

Services should check permissions, not role names.

---

# Resources

The system protects the following resources.

User

Library

Book

ReadingSession

ReadingProgress

Bookmark

Highlight

BookNote

Collection

Tag

ReadingGoal

Statistics

Notification

Attachment

Settings

---

# Standard Actions

Every resource may support:

Create

Read

Update

Delete

Archive

Restore

Export

Search

Manage

Not every resource supports every action.

---

# Permission Naming

Use the following format.

<Resource>:<Action>

Examples

Book:Create

Book:Read

Book:Update

Book:Archive

Highlight:Create

Highlight:Delete

ReadingSession:Create

ReadingGoal:Update

Statistics:Read

---

# Roles

Default roles.

Guest

AuthenticatedUser

Administrator

Roles should remain small.

Permissions should contain business capabilities.

---

# Guest Permissions

Guest may:

Register

Login

View public pages

Guest cannot access personal libraries.

---

# Authenticated User

May:

Manage own libraries

Manage own books

Manage own notes

Manage own highlights

Manage own goals

View own statistics

Export own knowledge

Users cannot access another user's data.

---

# Administrator

May:

Manage users

Manage system configuration

View system statistics

Manage storage

Manage notifications

Administrators do not automatically own user content.

Administrative access should be explicit.

---

# Ownership

Business data belongs to its owner.

Ownership must always be verified.

Example

User A

cannot update

User B's Book.

Ownership checks are mandatory.

---

# Authorization Rules

Always authenticate first.

Then verify ownership.

Then verify permissions.

Never rely only on client-side validation.

---

# API Security

Every endpoint should define:

Authentication required?

Ownership required?

Permission required?

Anonymous access?

---

# Service Layer

Authorization belongs in the Service layer.

Controllers should remain thin.

Repositories never perform authorization.

---

# Future Compatibility

The authorization model should support:

Teams

Organizations

Shared Libraries

Family Accounts

Workspace Collaboration

without redesign.

---

# AI Rules

When creating a new feature:

Define the protected resource.

Define supported actions.

Define required permissions.

Never hardcode role names inside business logic.

Prefer permission checks over role checks.
