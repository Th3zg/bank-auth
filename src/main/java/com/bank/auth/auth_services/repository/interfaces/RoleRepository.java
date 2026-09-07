package com.bank.auth.auth_services.repository.interfaces;

import com.bank.auth.auth_services.model.entity.CatalogsRole;
import io.vavr.control.Try;

import java.util.List;

public interface RoleRepository {
  Try<List<CatalogsRole>> findRoleByUserId(Long userId);
}
