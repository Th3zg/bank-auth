package com.bank.auth.services.repository.rowMapper;

import com.bank.auth.services.enums.UserStatus;
import com.bank.auth.services.model.SecurityAuthUserData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class SecurityAuthUserDataRowMapper implements RowMapper<SecurityAuthUserData> {
  @Override
  public SecurityAuthUserData mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new SecurityAuthUserData(
            rs.getLong("auth_user_id"),
            rs.getString("username"),
            rs.getString("password"),
            UserStatus.valueOf(rs.getString("status")),
            rs.getBoolean("account_non_locked"),
            rs.getBoolean("credentials_non_expired"),
            rs.getBoolean("email_verified"),
            rs.getBoolean("two_factor_enable"),
            rs.getBoolean("account_non_expired"),
            rs.getObject("last_password_change_at", OffsetDateTime.class)
    );
  }
}
