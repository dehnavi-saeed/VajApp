# VAJ Package Conventions

Version: 1.0

---

# Purpose

Defines the official package structure of VAJ.

Package names must represent business capabilities.

Packages are stable architectural boundaries.

---

# Root Package

app.vaj

---

# Top-Level Packages

app.vaj

    auth

    user

    library

    catalog

    reading

    knowledge

    analytics

    platform

    common

---

# Catalog Package

catalog

    book

    author

    publisher

    category

---

# Reading Package

reading

    session

    bookmark

    goal

---

# Knowledge Package

knowledge

    highlight

    note

    collection

    tag

---

# Analytics Package

analytics

    statistics

    dashboard

    projection

---

# Platform Package

platform

    search

    notification

    storage

    settings

---

# Shared Package

common

    exception

    security

    validation

    configuration

    util

    event

    valueobject

---

# Feature Layout

book

    api

    application

    domain

    infra

---

# API

controller

request

response

---

# Application

command

query

handler

service

policy

mapper

---

# Domain

aggregate

entity

valueobject

repository

event

service

specification

factory

exception

---

# Infrastructure

entity

repository

mapper

configuration

adapter

client

---

# Rules

Package names are singular.

Packages represent business concepts.

Packages never represent technologies.

Examples

book

reading

knowledge

Correct

Incorrect

controllers

services

repositories

dto

model

entity

---

# Cross Package Rules

Reading

may depend on

Catalog

Knowledge

may depend on

Catalog

Analytics

depends on everyone through Events.

Platform

depends on interfaces only.

---

# Forbidden

Circular dependencies.

Feature-to-feature entity sharing.

Infrastructure leakage.

Spring annotations in Domain.

---

# AI Rules

Always create packages using this structure.

Never invent new package layouts.

Never create util packages inside features.

Common code belongs only in common/.
