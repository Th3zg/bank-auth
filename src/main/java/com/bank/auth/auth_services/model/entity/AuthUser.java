package com.bank.auth.auth_services.model.entity;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
public record AuthUser(
        Long id,
        Long externalUserId,
        String username,
        String email,
        String password,
        String status,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean emailVerified,
        boolean twoFactorEnable,
        boolean isAccountNonExpired,
        OffsetDateTime lastLoginAt,
        OffsetDateTime lastPasswordChangeAt,
        int userTypeId,
        List<UserRole> roles) {
}
