# User Feature

Version: 1.0

Complexity
⭐⭐

Priority
Critical

---

# 1. Purpose

Represents the owner of all personal knowledge inside VAJ.

A User owns libraries, books, notes, highlights and goals.

Authentication is handled by the Auth feature.

The User feature manages identity, profile and ownership.

---

# 2. Vision

Every piece of knowledge in VAJ belongs to exactly one User.

Ownership is the foundation of authorization.

---

# 3. Scope

Included

• User Profile

• Profile Settings

• Avatar

• Language

• Timezone

• Account Status

• Preferences

Excluded

• Authentication

• Password Management

• JWT

• Sessions

---

# 4. Responsibilities

Owns

Profile

Preferences

Ownership

Personal Settings

Does NOT Own

Authentication

Books

Libraries

Reading

Statistics

Notifications

---

# 5. Domain Model

Aggregate Root

User

Entities

UserProfile

UserPreferences

Enums

UserStatus

Language

Theme

Timezone

---

# 6. Business Rules

Every User has exactly one Profile.

Every User owns one or more Libraries.

User data is private.

Deleted users cannot create new content.

Changing profile information must never affect ownership.

---

# 7. State Machine

Pending

↓

Active

↓

Suspended

↓

Deleted

Invalid Transition

Deleted → Active

---

# 8. Lifecycle

Create

Activate

Update Profile

Suspend

Delete

Restore (optional)

---

# 9. Commands

Create User

Update Profile

Upload Avatar

Update Preferences

Change Language

Change Timezone

Deactivate Account

Delete Account

---

# 10. Queries

Current User

User Profile

User Preferences

Owned Libraries

Owned Books

---

# 11. Use Cases

View Profile

Edit Profile

Upload Avatar

Update Preferences

Delete Account

---

# 12. Domain Events

UserCreated

UserActivated

ProfileUpdated

AvatarUpdated

PreferencesUpdated

UserSuspended

UserDeleted

---

# 13. Permissions

User:Read

User:Update

User:Delete

Profile:Update

Preferences:Update

---

# 14. REST API

GET    /users/me

PATCH  /users/me

POST   /users/me/avatar

GET    /users/me/preferences

PATCH  /users/me/preferences

DELETE /users/me

---

# 15. Database

Users

UserProfiles

UserPreferences

---

# 16. Validation

Display Name

1..100 characters

Avatar

Supported image types only

Timezone

Must be valid IANA timezone

Language

Must be supported

---

# 17. Search

Not searchable by default.

User privacy is preserved.

---

# 18. Non-Functional Requirements

Profile updates < 500ms

Avatar upload supports async processing

Audit profile changes

Soft Delete users

---

# 19. AI Rules

Never mix Authentication with User Profile.

Never expose internal identifiers.

Never expose deleted users.

User owns data.

User does not manage Authentication.

---

# 20. Test Scenarios

Create User

Update Profile

Upload Avatar

Delete User

Update Preferences

Invalid Language

Invalid Timezone

Suspended User

---

# 21. Future Evolution

Public Profiles

Following Users

Reading Friends

Shared Libraries

Achievements

Social Reading