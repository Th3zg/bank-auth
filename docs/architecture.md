# Auth Service Architecture

## Domain Events

### Consumed Events

- UserCreated
- UserActivated
- UserBlocked

These events originate from user-service
and may update the business status projection.

---

### Published Events

- UserAuthenticated
- UserLockedDueToAttempts
- PasswordChanged
- TwoFactorEnabled

These events may be consumed by other services
such as notification-service or audit-service.

---

## Critical Coordination

Fraud-related blocking requires synchronous REST coordination
from user-service to guarantee immediate consistency.

Kafka events are used for propagation,
not for security-critical enforcement.
