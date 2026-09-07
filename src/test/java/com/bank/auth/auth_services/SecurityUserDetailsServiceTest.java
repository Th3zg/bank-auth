package com.bank.auth.auth_services;

import com.bank.auth.auth_services.model.entity.AuthUser;
import com.bank.auth.auth_services.repository.AuthUserRepositoryImpl;
import com.bank.auth.auth_services.repository.RoleRepositoryImpl;
import com.bank.auth.auth_services.services.SecurityUserDetailsService;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SecurityUserDetailsServiceTest {

  @Mock
  private AuthUserRepositoryImpl authUserRepository;

  @Mock
  private RoleRepositoryImpl roleRepository;

  @InjectMocks
  private SecurityUserDetailsService userDetailsService;

  @Test
  void shouldLoadUserSuccessfully() {
    AuthUser authUser = AuthUser.builder()
            .id(1L)
            .externalUserId(100L)
            .username("Télios")
            .email("Telios@gamil.com")
            .password("hashed-password")
            .status("ACTIVE")
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .emailVerified(true)
            .twoFactorEnable(false)
            .isAccountNonExpired(true)
            .lastLoginAt(null)
            .lastPasswordChangeAt(null)
            .userTypeId(1)
            .roles(List.of())
            .build();

    when(authUserRepository.findByUserName("Télios"))
            .thenReturn(Try.success((Optional.of(authUser))));

    when(roleRepository.findRoleByUserId(1L))
            .thenReturn(Try.success(List.of()));

    UserDetails result = userDetailsService.loadUserByUsername("Télios");

    assertNotNull(result);
    assertEquals("Télios", result.getUsername());
    //assertEquals("Telios@gamil.com", result.
    assertEquals("hashed-password", result.getPassword());

    verify(authUserRepository).findByUserName("Télios");

  }
}
