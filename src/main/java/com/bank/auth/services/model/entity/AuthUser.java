package com.bank.auth.services.model.entity;

import com.bank.auth.services.enums.UserStatus;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record AuthUser(
        Long id,
        Long externalUserId,
        String username,
        String email,
        String password,
        UserStatus status,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean emailVerified,
        boolean twoFactorEnable,
        boolean isAccountNonExpired,
        OffsetDateTime lastLoginAt,
        OffsetDateTime lastPasswordChangeAt,
        UUID publicUserId,
        List<UserRole> roles) {
}
