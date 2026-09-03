package com.bank.auth.auth_services.services;

import com.bank.auth.auth_services.enums.AuthErrorCode;
import com.bank.auth.auth_services.model.entity.AuthUser;
import com.bank.auth.auth_services.repository.AuthUserRepositoryImpl;
import com.bank.auth.auth_services.util.Result;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepositoryImpl authUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Result<AuthUser, AuthErrorCode> login(
        String username,
        String password
    ) {
        return authUserRepository
            .findByUserName(username)
            .map(optUser ->
                optUser
                    .map(user -> {
                        // We verify the password
                        if (
                            bCryptPasswordEncoder.matches(
                                password,
                                user.getPassword()
                            )
                        ) {
                            // We update the last login
                            authUserRepository.updateLastLogin(user.getId());
                            return Result.<AuthUser, AuthErrorCode>success(
                                user
                            );
                        } else {
                            return Result.<AuthUser, AuthErrorCode>failure(
                                AuthErrorCode.INVALID_CREDENTIALS,
                                AuthErrorCode.INVALID_CREDENTIALS.getMessage()
                            );
                        }
                    })
                    .orElse(
                        Result.<AuthUser, AuthErrorCode>failure(
                            AuthErrorCode.USER_NOT_FOUND,
                            AuthErrorCode.USER_NOT_FOUND.getMessage()
                        )
                    )
            )
            .getOrElse(() ->
                Result.<AuthUser, AuthErrorCode>failure(
                    AuthErrorCode.INTERNAL_ERROR,
                    AuthErrorCode.INTERNAL_ERROR.getMessage()
                )
            );
    }
}
