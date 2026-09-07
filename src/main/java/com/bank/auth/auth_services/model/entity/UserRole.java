package com.bank.auth.auth_services.model.entity;

import lombok.Builder;

import java.time.OffsetDateTime;


@Builder
public record UserRole(
        Long authUserId,
        Long roleId,
        OffsetDateTime assignedAt,
        Long assignedBy) {
}
