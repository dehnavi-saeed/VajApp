# DEFINITION OF DONE (DoD)

Version: 1.0

This document defines when a task, feature, or module is considered complete.

No implementation is considered finished until **all** items in this checklist are satisfied.

---

# 1. Business Requirements

- All acceptance criteria are implemented.
- All business rules are respected.
- No assumptions have been introduced.
- Edge cases have been handled.

---

# 2. Architecture

- Follows DDD.
- Follows Clean Architecture.
- Follows Feature-Based structure.
- Respects the VAJ Constitution.
- No architectural violations.

---

# 3. Domain

- Entities protect their invariants.
- Value Objects are immutable.
- Domain rules remain inside Domain.
- No infrastructure dependency inside Domain.

---

# 4. Application Layer

- Use Cases are isolated.
- DTOs are defined.
- Validation exists.
- Exceptions are handled.
- Transactions are correct.

---

# 5. Infrastructure

- Repository implementation completed.
- Database migration added (Flyway).
- External services implemented.
- No hard-coded configuration.

---

# 6. API

- REST conventions respected.
- Proper HTTP status codes.
- Input validation.
- Error responses standardized.
- Swagger documentation added.

---

# 7. Security

- Authentication checked.
- Authorization checked.
- Input sanitized.
- Sensitive data protected.
- No secret exposed.

---

# 8. Database

- Migration tested.
- Constraints added.
- Indexes reviewed.
- Foreign keys correct.
- No unnecessary columns.

---

# 9. Code Quality

- No duplicated logic.
- No dead code.
- No TODO.
- No FIXME.
- No unused imports.
- No warnings.
- No magic numbers.

---

# 10. Readability

- Good naming.
- Small methods.
- Small classes.
- Self-explanatory code.
- Comments only when necessary.

---

# 11. Performance

- No N+1 queries.
- Pagination where required.
- Lazy/Eager loading reviewed.
- No unnecessary allocations.

---

# 12. Testing

The feature includes:

- Unit Tests
- Integration Tests
- Repository Tests (if applicable)

Tests are:

- Readable
- Independent
- Repeatable

All tests pass.

---

# 13. Frontend (Angular)

If UI changes exist:

- Responsive
- Accessible
- Proper error handling
- Loading states
- Empty states
- Validation messages

---

# 14. Documentation

Updated if needed:

- API Documentation
- Feature Documentation
- Architecture Documentation

---

# 15. Logging

- Meaningful logs.
- No sensitive information.
- Errors logged.
- Debug logs removed.

---

# 16. Git

Before completion:

- Code formatted.
- Project builds successfully.
- Commit message prepared.
- Ready for Pull Request.

---

# 17. AI Review Checklist

Before considering the task complete, AI must verify:

□ Architecture is correct.

□ Business rules are respected.

□ Security reviewed.

□ Performance reviewed.

□ Tests added.

□ Documentation updated.

□ Code compiles.

□ No placeholders.

□ No duplicated logic.

□ No unfinished work.

---

# 18. Completion Rule

A task is considered DONE only when:

- Build succeeds.
- Tests pass.
- Review checklist passes.
- Documentation is updated.
- No critical issues remain.

Otherwise, the task status is:

NOT DONE
