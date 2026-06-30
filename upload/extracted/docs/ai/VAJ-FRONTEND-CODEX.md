# VAJ-FRONTEND-CODEX.md

Version: 2.0

Status: Official

---

# Purpose

This document defines every frontend development rule for VAJ.

Every AI assistant generating Angular code MUST follow these rules.

If generated code violates these rules,

the generated code is considered incorrect.

---

# Frontend Technology Stack

Framework

Angular 20+

Language

TypeScript

Architecture

Feature-Based Architecture

UI Library

Angular Material

Styling

Tailwind CSS

Icons

Material Symbols

Charts

ApexCharts

Testing

Jasmine

Karma

Playwright (Future)

---

# Frontend Philosophy

The Frontend presents information.

The Backend protects business rules.

The Domain owns business decisions.

Never move business logic from Backend to Frontend.

---

# Primary Responsibilities

Frontend is responsible for:

Presentation

Navigation

User Interaction

Client-side Validation

API Communication

State Presentation

Nothing more.

---

# Architectural Style

Feature-Based

Standalone Components

Lazy Loaded Features

Shared UI Components

Core Services

No NgModules unless technically required.

---

# Folder Structure

src/

app/

core/

shared/

layout/

features/

assets/

environments/

---

# Core Layer

Contains

Authentication

Guards

HTTP Interceptors

Configuration

API Client

Global Services

Error Handling

Core is loaded once.

---

# Shared Layer

Contains

Reusable Components

Directives

Pipes

Validators

Utilities

Shared contains no business logic.

---

# Layout Layer

Contains

Main Layout

Authentication Layout

Navigation

Toolbar

Sidebar

Footer

Layout contains no feature logic.

---

# Feature Layer

Each Feature owns:

Pages

Components

Services

Routes

Models

Validators

Feature-specific UI

Every Feature is isolated.

---

# Dependency Rules

Allowed

Feature

↓

Core

↓

Shared

Forbidden

Feature → Feature

Shared → Feature

Layout → Feature

Never create circular dependencies.

---

# Routing Rules

Every Feature owns its own routes.

Use Lazy Loading.

Protect routes with Guards when required.

Avoid deeply nested routing.

---

# Standalone Components

Use Standalone Components by default.

Avoid unnecessary NgModules.

Each component imports only required dependencies.

---

# Component Responsibilities

Components:

Display UI

Receive User Input

Call Services

Render State

Components never contain business rules.

---

# Service Responsibilities

Services:

Call REST APIs

Transform responses

Handle HTTP errors

Coordinate UI state

Services never implement business decisions.

---

# Model Rules

Frontend models mirror API contracts.

Frontend models are not Domain models.

Never recreate backend business objects.

Use interfaces where appropriate.

---

# API Communication

All HTTP communication goes through dedicated Services.

Never call HttpClient directly from Components.

Never duplicate endpoints.

Centralize API access.

---

# Error Handling

Handle errors consistently.

Display user-friendly messages.

Log technical details only when appropriate.

Never expose backend exception details.

---

# Loading Strategy

Every async operation must have a loading state.

Never block the UI unnecessarily.

Prefer skeleton loaders over spinners for larger views.

---

# Accessibility

Follow WCAG guidelines.

Support keyboard navigation.

Use semantic HTML.

Provide accessible labels.

Color must never be the only indicator of state.

---

# Responsive Design

Mobile First.

Support Desktop.

Support Tablet.

Avoid fixed pixel layouts.

Prefer Flexbox and CSS Grid.

---

# General Principle

Angular is responsible for user experience.

Business correctness always belongs to the Backend.

# Angular Coding Standards

Always use the latest stable Angular version approved by the project.

Prefer Angular built-in features over third-party libraries.

Avoid unnecessary dependencies.

---

# TypeScript Rules

Always enable strict mode.

Never use "any".

Prefer explicit types.

Use readonly whenever possible.

Prefer interfaces for API contracts.

Prefer enums only when business meaning exists.

---

# Angular Signals

Prefer Signals for local UI state.

Use computed() for derived state.

Use effect() only when side effects are required.

Do not misuse Signals as a global state management solution.

---

# State Management

Use local state whenever possible.

Avoid introducing NgRx unless project complexity requires it.

State belongs to the closest responsible component.

Backend remains the source of truth.

---

# Reactive Forms

Always use Reactive Forms.

Never use Template Driven Forms.

Every form must include:

Validation

Error messages

Loading state

Submit state

Reset behavior

---

# Form Validation

Client validation improves user experience.

Server validation guarantees correctness.

Never rely only on frontend validation.

Validation messages must be user friendly.

---

# HTTP Communication

All HTTP requests go through dedicated Services.

Never call HttpClient from Components.

Never duplicate endpoint definitions.

Centralize API URLs.

Example

Good

BookComponent

↓

BookService

↓

ApiClient

↓

Backend

Bad

BookComponent

↓

HttpClient

---

# API Services

Each Feature owns its own Service.

Examples

BookService

ReadingService

KnowledgeService

SearchService

Services expose business-oriented methods.

Avoid generic "request()" methods.

---

# Interceptors

Use Interceptors for:

Authentication

Logging

Error handling

Correlation IDs

Request headers

Never place business logic inside an Interceptor.

---

# Authentication Flow

Login

↓

Receive JWT

↓

Store securely

↓

Attach JWT through Interceptor

↓

Refresh when needed

↓

Logout

Never manually attach Authorization headers in Components.

---

# Guards

Use Guards only for navigation decisions.

Examples

Authentication

Authorization

Feature availability

Guards never load business data.

---

# Resolvers

Resolvers are optional.

Use only when the page cannot render without initial data.

Avoid excessive Resolver usage.

---

# Routing Rules

Each Feature owns its own routing.

Prefer shallow routes.

Examples

/books

/books/create

/books/:id

/books/:id/edit

Avoid deeply nested routing.

---

# Component Communication

Prefer:

Input()

↓

Output()

↓

Service

Avoid component chains.

Avoid global mutable state.

---

# Smart vs Dumb Components

Smart Components

Load data

Call Services

Manage state

Dumb Components

Display data

Raise events

No HTTP

No business logic

---

# API Models

Separate:

Request Models

Response Models

View Models

Never reuse backend DTOs blindly.

View Models may enrich presentation.

---

# Mapping

API Response

↓

Mapper

↓

View Model

↓

Component

Never map inside HTML templates.

---

# Loading States

Every asynchronous action must expose:

Loading

Success

Error

Empty

Never leave users without feedback.

---

# Error States

Every page should gracefully handle:

404

403

500

Network failure

Timeout

Display meaningful messages.

---

# Search

Debounce user input.

Cancel obsolete requests.

Avoid sending a request on every keystroke.

Use pagination where appropriate.

---

# Pagination

Server-side pagination preferred.

Expose:

Current page

Page size

Total records

Sorting

Filtering

Never load huge datasets into the browser.

---

# Sorting

Sorting should be performed by the backend whenever possible.

Frontend sorting is acceptable only for small local datasets.

---

# Filtering

Filtering should be explicit.

Do not hide active filters.

Allow filter reset.

---

# Date & Time

Backend sends UTC.

Frontend converts to local timezone.

Never hardcode date formats.

Use centralized formatting utilities.

---

# Internationalization

Prepare components for localization.

Avoid hardcoded strings.

Keep UI text centralized.

---

# File Upload

Use dedicated upload services.

Display upload progress.

Validate file size.

Validate file type.

Handle upload failures gracefully.

---

# Download

Show progress when applicable.

Use meaningful filenames.

Handle failed downloads correctly.

---

# Notifications

Use Snackbar for temporary messages.

Use Dialogs for confirmation.

Never use browser alert().

---

# General Principle

Frontend should feel fast.

Backend should remain authoritative.

Never trade correctness for convenience.

# UI Philosophy

The UI should be:

Simple

Consistent

Predictable

Accessible

Responsive

Avoid visual complexity.

The interface should help users focus on reading and knowledge.

---

# Design System

Every UI element must follow the Design System.

Never create custom styles when a standard component already exists.

Consistency is more important than creativity.

---

# Angular Material

Angular Material is the primary UI library.

Prefer Material components over custom implementations.

Examples

MatTable

MatDialog

MatMenu

MatTabs

MatPaginator

MatTooltip

MatSnackBar

MatIcon

---

# Tailwind CSS

Tailwind is used for:

Spacing

Layout

Typography

Responsive Design

Utility Classes

Never use Tailwind to replace Angular Material components.

---

# Theme Rules

Support:

Light Theme

Dark Theme

Future Custom Themes

Never hardcode colors.

Use theme variables.

---

# Color Rules

Colors communicate meaning.

Primary

Navigation

Secondary

Supporting actions

Success

Completed operations

Warning

Requires attention

Error

Failed operations

Information

Neutral feedback

Never use color as the only indicator.

---

# Typography

Use a consistent typography scale.

Limit the number of font sizes.

Avoid inline font styling.

Use predefined typography classes.

---

# Icons

Use Material Symbols.

Use icons consistently.

Every icon should have semantic meaning.

Avoid decorative icons.

---

# Layout Rules

Use Flexbox by default.

Use CSS Grid for dashboards.

Avoid fixed widths.

Support all common screen sizes.

---

# Page Layout

Every feature page follows the same structure.

Page Header

↓

Toolbar / Actions

↓

Filters (optional)

↓

Content

↓

Pagination

↓

Footer (optional)

---

# Data Tables

Large datasets use server-side pagination.

Every table supports:

Sorting

Filtering

Pagination

Loading state

Empty state

Error state

Selection (if required)

Avoid horizontal scrolling where possible.

---

# Dialogs

Dialogs are used only for:

Confirmation

Short Forms

Critical Information

Do not build complete workflows inside dialogs.

---

# Forms

Every form provides:

Clear labels

Validation

Required indicators

Helpful hints

Consistent spacing

Submit state

Cancel action

Success feedback

---

# Buttons

Use consistent priorities.

Primary

Main action

Secondary

Alternative action

Text

Low priority

Warn

Destructive action

Never place two Primary buttons together.

---

# Navigation

Navigation must be predictable.

Keep menu hierarchy shallow.

Highlight current location.

Avoid hidden navigation.

---

# Dashboard

Dashboards display:

Summary

Statistics

Recent Activity

Quick Actions

Never overload dashboards with details.

---

# Search UX

Search should provide:

Instant feedback

Loading indicator

Empty state

Recent searches (future)

Clear search option

Search results should be easy to scan.

---

# Empty States

Every empty page should explain:

Why it is empty.

What the user can do next.

Provide a primary action when appropriate.

---

# Error Pages

Provide dedicated pages for:

401

403

404

500

Offline

Avoid technical language.

Explain what happened.

Offer recovery actions.

---

# Loading States

Prefer skeleton loaders.

Avoid long blocking spinners.

Loading indicators must not cause layout shifts.

---

# Notifications

Snackbars

Temporary feedback

Dialogs

User decisions

Badges

Unread counts

Banners

Important announcements

Never interrupt users unnecessarily.

---

# Accessibility

Support keyboard navigation.

Support screen readers.

Provide sufficient color contrast.

Use semantic HTML.

Label interactive elements.

Focus management is mandatory for dialogs.

---

# Responsive Design

Design Mobile First.

Support:

Mobile

Tablet

Desktop

Ultra-wide monitors

Avoid separate mobile implementations.

---

# Performance

Lazy load Features.

Lazy load large dialogs.

Use OnPush-compatible patterns.

Avoid unnecessary DOM updates.

Track lists efficiently.

Minimize bundle size.

---

# Images

Optimize all images.

Prefer SVG for icons.

Use responsive images.

Avoid large uncompressed assets.

---

# Animations

Animations should be subtle.

Support user reduced-motion preferences.

Never slow down interactions.

---

# Component Design

Every reusable component should:

Have a single purpose.

Expose a minimal API.

Be independently testable.

Avoid hidden side effects.

---

# Naming

Components

book-list.component.ts

book-detail.component.ts

book-edit.component.ts

Services

book.service.ts

reading.service.ts

Dialogs

delete-book-dialog.component.ts

Pipes

reading-time.pipe.ts

Directives

autofocus.directive.ts

---

# General Principle

Users should immediately understand the interface.

The best UI is the one that requires the least explanation.

# UI Philosophy

The UI should be:

Simple

Consistent

Predictable

Accessible

Responsive

Avoid visual complexity.

The interface should help users focus on reading and knowledge.

---

# Design System

Every UI element must follow the Design System.

Never create custom styles when a standard component already exists.

Consistency is more important than creativity.

---

# Angular Material

Angular Material is the primary UI library.

Prefer Material components over custom implementations.

Examples

MatTable

MatDialog

MatMenu

MatTabs

MatPaginator

MatTooltip

MatSnackBar

MatIcon

---

# Tailwind CSS

Tailwind is used for:

Spacing

Layout

Typography

Responsive Design

Utility Classes

Never use Tailwind to replace Angular Material components.

---

# Theme Rules

Support:

Light Theme

Dark Theme

Future Custom Themes

Never hardcode colors.

Use theme variables.

---

# Color Rules

Colors communicate meaning.

Primary

Navigation

Secondary

Supporting actions

Success

Completed operations

Warning

Requires attention

Error

Failed operations

Information

Neutral feedback

Never use color as the only indicator.

---

# Typography

Use a consistent typography scale.

Limit the number of font sizes.

Avoid inline font styling.

Use predefined typography classes.

---

# Icons

Use Material Symbols.

Use icons consistently.

Every icon should have semantic meaning.

Avoid decorative icons.

---

# Layout Rules

Use Flexbox by default.

Use CSS Grid for dashboards.

Avoid fixed widths.

Support all common screen sizes.

---

# Page Layout

Every feature page follows the same structure.

Page Header

↓

Toolbar / Actions

↓

Filters (optional)

↓

Content

↓

Pagination

↓

Footer (optional)

---

# Data Tables

Large datasets use server-side pagination.

Every table supports:

Sorting

Filtering

Pagination

Loading state

Empty state

Error state

Selection (if required)

Avoid horizontal scrolling where possible.

---

# Dialogs

Dialogs are used only for:

Confirmation

Short Forms

Critical Information

Do not build complete workflows inside dialogs.

---

# Forms

Every form provides:

Clear labels

Validation

Required indicators

Helpful hints

Consistent spacing

Submit state

Cancel action

Success feedback

---

# Buttons

Use consistent priorities.

Primary

Main action

Secondary

Alternative action

Text

Low priority

Warn

Destructive action

Never place two Primary buttons together.

---

# Navigation

Navigation must be predictable.

Keep menu hierarchy shallow.

Highlight current location.

Avoid hidden navigation.

---

# Dashboard

Dashboards display:

Summary

Statistics

Recent Activity

Quick Actions

Never overload dashboards with details.

---

# Search UX

Search should provide:

Instant feedback

Loading indicator

Empty state

Recent searches (future)

Clear search option

Search results should be easy to scan.

---

# Empty States

Every empty page should explain:

Why it is empty.

What the user can do next.

Provide a primary action when appropriate.

---

# Error Pages

Provide dedicated pages for:

401

403

404

500

Offline

Avoid technical language.

Explain what happened.

Offer recovery actions.

---

# Loading States

Prefer skeleton loaders.

Avoid long blocking spinners.

Loading indicators must not cause layout shifts.

---

# Notifications

Snackbars

Temporary feedback

Dialogs

User decisions

Badges

Unread counts

Banners

Important announcements

Never interrupt users unnecessarily.

---

# Accessibility

Support keyboard navigation.

Support screen readers.

Provide sufficient color contrast.

Use semantic HTML.

Label interactive elements.

Focus management is mandatory for dialogs.

---

# Responsive Design

Design Mobile First.

Support:

Mobile

Tablet

Desktop

Ultra-wide monitors

Avoid separate mobile implementations.

---

# Performance

Lazy load Features.

Lazy load large dialogs.

Use OnPush-compatible patterns.

Avoid unnecessary DOM updates.

Track lists efficiently.

Minimize bundle size.

---

# Images

Optimize all images.

Prefer SVG for icons.

Use responsive images.

Avoid large uncompressed assets.

---

# Animations

Animations should be subtle.

Support user reduced-motion preferences.

Never slow down interactions.

---

# Component Design

Every reusable component should:

Have a single purpose.

Expose a minimal API.

Be independently testable.

Avoid hidden side effects.

---

# Naming

Components

book-list.component.ts

book-detail.component.ts

book-edit.component.ts

Services

book.service.ts

reading.service.ts

Dialogs

delete-book-dialog.component.ts

Pipes

reading-time.pipe.ts

Directives

autofocus.directive.ts

---

# General Principle

Users should immediately understand the interface.

The best UI is the one that requires the least explanation.
