package com.bank.auth.auth_services.repository.interfaces;

import com.bank.auth.auth_services.model.entity.AuthUser;
import io.vavr.control.Try;

import java.util.Optional;

public interface AuthUserRepository {
  Try<Optional<AuthUser>> findByUserName(String username);
  boolean existByUsername(String username);
  void updateLastLogin(Long userId);
}
