package com.bank.auth.auth_services.model.entity;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record CatalogsRole(
        Long roleId,
        String code,
        String name,
        String description,
        boolean active,
        boolean systemRole,
        String scope,
        boolean deprecated,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long createdBy,
        Long updatedBy
) {}
