package com.bank.auth.auth_services.model;

import com.bank.auth.auth_services.enums.UserStatus;
import com.bank.auth.auth_services.model.entity.AuthUser;
import com.bank.auth.auth_services.model.entity.CatalogsRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityAuthUser implements UserDetails {
  private final AuthUser user;
  private final List<CatalogsRole> roles;

  public SecurityAuthUser(AuthUser user, List<CatalogsRole> roles) {
    this.user = user;
    this.roles = roles;
  }

  @Override
  public String getUsername() {
    return user.username();
  }

  @Override
  public String getPassword() {
    return user.password();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
            .map(role -> new SimpleGrantedAuthority(
                    "ROLE_" + role.code().value()
            )).toList();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.accountNonLocked();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user.isAccountNonExpired();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user.credentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return user.status() == UserStatus.ACTIVE;
  }
}
