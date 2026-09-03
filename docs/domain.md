## Entity: AuthUser

Represents the authenticable identity within the system.

It does not contain civil or personal user information.
It is related to user-service through `external_user_id`.

---

## Responsibilities

- Credentials management
- Authentication state
- Technical account locking
- 2FA management
- Business status projection (minimal)

---

## Business Status Projection

Auth-service stores a minimal projection of business state:

- business_status: ACTIVE | BLOCKED | SUSPENDED

This projection is updated synchronously
when user-service performs a fraud-related state change.

Auth-service is not the Source of Truth
for business state, but maintains it
for immediate login validation.

---

## Lock Types

### Technical Lock
Managed exclusively by auth-service.

Examples:
- Excessive failed login attempts
- 2FA failures
- Credential compromise

---

### Business Lock
Originates in user-service.

Examples:
- Fraud detection
- Regulatory suspension
- Legal restriction

Business locks are synchronized immediately via REST.

---

## Authentication Validation Rule

A login attempt is valid only if:

1. Credentials are valid.
2. AuthUser is not technically locked.
3. business_status projection is ACTIVE.
