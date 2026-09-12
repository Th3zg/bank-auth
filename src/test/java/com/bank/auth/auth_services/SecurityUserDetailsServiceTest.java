package com.bank.auth.auth_services;

import com.bank.auth.auth_services.enums.RoleCode;
import com.bank.auth.auth_services.enums.UserStatus;
import com.bank.auth.auth_services.model.entity.AuthUser;
import com.bank.auth.auth_services.model.entity.CatalogsRole;
import com.bank.auth.auth_services.repository.AuthUserRepositoryImpl;
import com.bank.auth.auth_services.repository.RoleRepositoryImpl;
import com.bank.auth.auth_services.services.SecurityUserDetailsService;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
              .status(UserStatus.ACTIVE)
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

      CatalogsRole customerBasic = CatalogsRole.builder()
              .roleId(1L)
              .code(RoleCode.CUSTOMER_BASIC)
              .build();

      when(authUserRepository.findByUserName("Télios"))
              .thenReturn(Try.success((Optional.of(authUser))));

      when(roleRepository.findRoleByUserId(1L))
              .thenReturn(Try.success(List.of(customerBasic)));

      UserDetails result = userDetailsService.loadUserByUsername("Télios");

      assertNotNull(result);
      assertEquals("Télios", result.getUsername());
      assertEquals("hashed-password", result.getPassword());
      assertEquals(1, result.getAuthorities().size());
      assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER_BASIC")));
      assertTrue(result.isAccountNonLocked());
      assertTrue(result.isAccountNonExpired());
      assertTrue(result.isCredentialsNonExpired());
      assertTrue(result.isEnabled());

      verify(authUserRepository).findByUserName("Télios");
      verify(roleRepository).findRoleByUserId(1L);
    }

    @Test
    void shouldLoadUserWithMultipleRoles() {
      AuthUser authUser = AuthUser.builder()
              .id(1L)
              .externalUserId(100L)
              .username("Télios")
              .email("Telios@gamil.com")
              .password("hashed-password")
              .status(UserStatus.ACTIVE)
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

      CatalogsRole customerBasic = CatalogsRole.builder()
              .roleId(1L)
              .code(RoleCode.CUSTOMER_BASIC)
              .build();

      CatalogsRole employeeAnalyst = CatalogsRole.builder()
              .roleId(2L)
              .code(RoleCode.EMPLOYEE_ANALYST)
              .build();

      CatalogsRole employeeCompliance = CatalogsRole.builder()
              .roleId(3L)
              .code(RoleCode.EMPLOYEE_COMPLIANCE)
              .build();

      when(authUserRepository.findByUserName("Télios"))
              .thenReturn(Try.success(Optional.of(authUser)));

      when(roleRepository.findRoleByUserId(1L))
              .thenReturn(Try.success(List.of(
                      customerBasic,
                      employeeAnalyst,
                      employeeCompliance
              )));

      UserDetails result = userDetailsService.loadUserByUsername("Télios");

      assertNotNull(result);
      assertEquals("Télios", result.getUsername());
      assertEquals("hashed-password", result.getPassword());
      assertEquals(3, result.getAuthorities().size());
      assertTrue(result.getAuthorities().contains(
              new SimpleGrantedAuthority("ROLE_CUSTOMER_BASIC")
      ));
      assertTrue(result.getAuthorities().contains(
              new SimpleGrantedAuthority("ROLE_EMPLOYEE_ANALYST")
      ));
      assertTrue(result.getAuthorities().contains(
              new SimpleGrantedAuthority("ROLE_EMPLOYEE_COMPLIANCE")
      ));
      assertTrue(result.isAccountNonLocked());
      assertTrue(result.isAccountNonExpired());
      assertTrue(result.isCredentialsNonExpired());
      assertTrue(result.isEnabled());

      verify(authUserRepository).findByUserName("Télios");
      verify(roleRepository).findRoleByUserId(1L);
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
      when(authUserRepository.findByUserName("Télios"))
              .thenReturn(Try.success(Optional.empty()));

      assertThrows(UsernameNotFoundException.class,
              () -> userDetailsService.loadUserByUsername("Télios"));

      verify(authUserRepository).findByUserName("Télios");
      verifyNoInteractions(roleRepository);
    }

    @Test
    void shouldLoadInactiveUserAsDisabled() {
      AuthUser authUser = AuthUser.builder()
              .id(1L)
              .externalUserId(100L)
              .username("Télios")
              .email("Telios@gmail.com")
              .password("hashed-password")
              .status(UserStatus.INACTIVE)
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
              .thenReturn(Try.success(Optional.of(authUser)));

      when(roleRepository.findRoleByUserId(1L))
              .thenReturn(Try.success(List.of()));

      UserDetails result = userDetailsService.loadUserByUsername("Télios");

      assertFalse(result.isEnabled());

      verify(authUserRepository).findByUserName("Télios");
      verify(roleRepository).findRoleByUserId(1L);
    }

    @Test
    void shouldLoadLockedUserAsLocked() {
      AuthUser authUser = AuthUser.builder()
              .id(1L)
              .externalUserId(100L)
              .username("Télios")
              .email("Telios@gmail.com")
              .password("hashed-password")
              .status(UserStatus.ACTIVE)
              .accountNonLocked(false)
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
              .thenReturn(Try.success(Optional.of(authUser)));

      when(roleRepository.findRoleByUserId(1L))
              .thenReturn(Try.success(List.of()));

      UserDetails result = userDetailsService.loadUserByUsername("Télios");

      assertFalse(result.isAccountNonLocked());
    }

    @Test
    void shouldLoadExpiredAccountAsExpired() {
      AuthUser authUser = AuthUser.builder()
              .id(1L)
              .externalUserId(100L)
              .username("Télios")
              .email("Telios@gmail.com")
              .password("hashed-password")
              .status(UserStatus.ACTIVE)
              .accountNonLocked(true)
              .credentialsNonExpired(true)
              .emailVerified(true)
              .twoFactorEnable(false)
              .isAccountNonExpired(false)
              .lastLoginAt(null)
              .lastPasswordChangeAt(null)
              .userTypeId(1)
              .roles(List.of())
              .build();

      when(authUserRepository.findByUserName("Télios"))
              .thenReturn(Try.success(Optional.of(authUser)));

      when(roleRepository.findRoleByUserId(1L))
              .thenReturn(Try.success(List.of()));

      UserDetails result = userDetailsService.loadUserByUsername("Télios");

      assertFalse(result.isAccountNonExpired());

      verify(authUserRepository).findByUserName("Télios");
      verify(roleRepository).findRoleByUserId(1L);
    }
}
