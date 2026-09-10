package com.bank.auth.auth_services.repository.rowMapper;

import com.bank.auth.auth_services.enums.RoleCode;
import com.bank.auth.auth_services.model.entity.CatalogsRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class CatalogsRoleRowMapper implements RowMapper<CatalogsRole> {
  @Override
  public CatalogsRole mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new CatalogsRole(
            rs.getLong("role_id"),
            RoleCode.valueOf(rs.getString("code")),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBoolean("active"),
            rs.getBoolean("is_system_role"),
            rs.getString("scope"),
            rs.getBoolean("deprecated"),
            rs.getObject("created_at", OffsetDateTime.class),
            rs.getObject("updated_at", OffsetDateTime.class),
            rs.getObject("created_by", Long.class),
            rs.getObject("updated_by", Long.class)
    );
  }
}
