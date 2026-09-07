package com.bank.auth.auth_services.model.entity;

import java.time.OffsetDateTime;

public record Permission(
        Long id,
        String code,
        String name,
        String description,
        String resource,
        String action,
        boolean active,
        boolean systemPermission,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long createdBy,
        Long updatedBy
) {
}