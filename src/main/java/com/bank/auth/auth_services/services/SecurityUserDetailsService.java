package com.bank.auth.auth_services.services;

import com.bank.auth.auth_services.enums.UserStatus;
import com.bank.auth.auth_services.model.entity.AuthUser;
import com.bank.auth.auth_services.model.entity.CatalogsRole;
import com.bank.auth.auth_services.repository.AuthUserRepositoryImpl;
import com.bank.auth.auth_services.repository.RoleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
  private final AuthUserRepositoryImpl authUserRepository;
  private final RoleRepositoryImpl roleRepositoryImpl;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AuthUser authUser = authUserRepository.findByUserName(username)
            .get()
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    List<CatalogsRole> roles = roleRepositoryImpl.findRoleByUserId(authUser.id()).get();
    List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name().toUpperCase()))
            .toList();

    return User.builder()
            .username(authUser.username())
            .password(authUser.password())
            .authorities(authorities)
            .accountLocked(!authUser.accountNonLocked())
            .accountExpired(!authUser.isAccountNonExpired())
            .credentialsExpired(!authUser.credentialsNonExpired())
            .disabled(authUser.status().equals(UserStatus.ACTIVE))
            .build();
  }
}
