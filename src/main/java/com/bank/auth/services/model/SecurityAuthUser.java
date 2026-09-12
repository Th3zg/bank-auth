package com.bank.auth.services.model;

import com.bank.auth.services.enums.UserStatus;
import com.bank.auth.services.model.entity.CatalogsRole;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Builder
public class SecurityAuthUser implements UserDetails {
  private final SecurityAuthUserData user;
  private final List<CatalogsRole> roles;

  public SecurityAuthUser(SecurityAuthUserData user, List<CatalogsRole> roles) {
    this.user = user;
    this.roles = roles;
  }

  public Long getId() {
    return user.id();
  }

  @Override
  public String getUsername() {
    return user.username();
  }

  @Override
  public String getPassword() {
    return user.password();
  }

  public UserStatus getStatus() {
    return user.status();
  }

  public boolean isEmailVerified() {
    return user.emailVerified();
  }

  public boolean isTwoFactorEnable() {
    return user.twoFactorEnable();
  }

  public OffsetDateTime getLastPasswordChangeAt() {
    return user.lastPasswordChangeAt();
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
    return user.accountNonExpired();
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
