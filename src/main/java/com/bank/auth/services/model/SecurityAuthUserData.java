package com.bank.auth.services.model;

import com.bank.auth.services.enums.UserStatus;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record SecurityAuthUserData (
        Long id,
        String username,
        String password,
        UserStatus status,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean emailVerified,
        boolean twoFactorEnable,
        boolean accountNonExpired,
        OffsetDateTime lastPasswordChangeAt
) {
}
