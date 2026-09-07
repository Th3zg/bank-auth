package com.bank.auth.auth_services.model.entity;

import java.time.OffsetDateTime;

public record RolePermission(
        Long roleId,
        Long permissionId,
        OffsetDateTime grantedAt,
        Long grantedBy
) {
}