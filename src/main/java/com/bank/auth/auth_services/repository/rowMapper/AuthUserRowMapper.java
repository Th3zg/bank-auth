package com.bank.auth.auth_services.repository.rowMapper;

import com.bank.auth.auth_services.model.entity.AuthUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class AuthUserRowMapper implements RowMapper<AuthUser> {
  @Override
  public AuthUser mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new AuthUser(
            rs.getLong("auth_user_id"),
            rs.getLong("external_user_id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("status"),
            rs.getBoolean("account_non_locked"),
            rs.getBoolean("credentials_non_expired"),
            rs.getBoolean("email_verified"),
            rs.getBoolean("two_factor_enable"),
            rs.getBoolean("account_non_expired"),
            rs.getObject("last_login_at", OffsetDateTime.class),
            rs.getObject("last_password_change_at", OffsetDateTime.class),
            rs.getInt("user_type_id"),
            List.of()
    );
  }
}
