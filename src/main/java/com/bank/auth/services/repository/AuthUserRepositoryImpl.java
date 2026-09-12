package com.bank.auth.services.repository;

import com.bank.auth.services.model.SecurityAuthUserData;
import com.bank.auth.services.model.entity.AuthUser;
import com.bank.auth.services.repository.interfaces.AuthUserRepository;
import com.bank.auth.services.repository.rowMapper.AuthUserRowMapper;
import com.bank.auth.services.repository.rowMapper.SecurityAuthUserDataRowMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthUserRepositoryImpl implements AuthUserRepository {
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final AuthUserRowMapper authUserRowMapper;
  private final SecurityAuthUserDataRowMapper securityAuthUserDataRowMapper;

  @Override
  public Try<Optional<SecurityAuthUserData>> findSecurityUserByUsername(String username) {
    String sql = """
            SELECT
              auth_user_id,
              external_user_id,
              username,
              password,
              status,
              account_non_locked,
              credentials_non_expired,
              email_verified,
              two_factor_enable,
              account_non_expired,
              last_password_change_at
            FROM auth.auth_users
            WHERE username = :username
            """;

    Map<String, Object> params = Map.of("username", username);
    return Try.of(() ->
            namedParameterJdbcTemplate.query(sql, params, securityAuthUserDataRowMapper).stream().findFirst());
  }

  @Override
  public Try<Optional<AuthUser>> findByUserName(String username) {
    String sql = """
            SELECT * FROM auth.auth_users WHERE username = :username
            """;

    Map<String, Object> params = Map.of("username", username);
    return Try.of(() ->
            namedParameterJdbcTemplate.query(sql, params, authUserRowMapper).stream().findFirst()
    );
  }

  public boolean existByUsername(String username) {
    String sql = """
            SELECT EXISTS(SELECT 1 FROM auth.auth_users WHERE username = :username);
            """;

    Map<String, Object> params = Map.of("username", username);
    return Boolean.TRUE.equals(
            namedParameterJdbcTemplate.queryForObject(sql, params, Boolean.class));
  }

  @Override
  public void updateLastLogin(Long userId) {
    String sql = """
            UPDATE auth.auth_users SET last_login_at = NOW() WHERE auth_user_id = :id
            """;

    Map<String, Object> params = Map.of("id", userId);
    namedParameterJdbcTemplate.update(sql, params);
  }
}
