package com.bank.auth.services.repository.interfaces;

import com.bank.auth.services.model.SecurityAuthUserData;
import com.bank.auth.services.model.entity.AuthUser;
import io.vavr.control.Try;

import java.util.Optional;

public interface AuthUserRepository {
  Try<Optional<SecurityAuthUserData>> findSecurityUserByUsername(String username);
  Try<Optional<AuthUser>> findByUserName(String username);
  boolean existByUsername(String username);
  void updateLastLogin(Long userId);
}
