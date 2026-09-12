package com.bank.auth.services.services;

import com.bank.auth.services.model.SecurityAuthUser;
import com.bank.auth.services.model.SecurityAuthUserData;
import com.bank.auth.services.model.entity.AuthUser;
import com.bank.auth.services.model.entity.CatalogsRole;
import com.bank.auth.services.repository.AuthUserRepositoryImpl;
import com.bank.auth.services.repository.RoleRepositoryImpl;
import lombok.RequiredArgsConstructor;
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
    SecurityAuthUserData securityAuthUserData = authUserRepository.findSecurityUserByUsername(username)
            .get()
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    List<CatalogsRole> roles = roleRepositoryImpl.findRoleByUserId(securityAuthUserData.id()).get();

    return SecurityAuthUser.builder()
            .user(securityAuthUserData)
            .roles(roles)
            .build();
  }
}
