package com.bank.auth.auth_services.model.entity;

import com.bank.auth.auth_services.enums.RoleCode;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record CatalogsRole(
        Long roleId,
        RoleCode code,
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
