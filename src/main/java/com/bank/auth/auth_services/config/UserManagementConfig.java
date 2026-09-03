package com.bank.auth.auth_services.config;

import com.bank.auth.auth_services.services.SecurityUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
public class UserManagementConfig {
  private final SecurityUserDetailsService userDetailsService;

  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

//  @Bean
//  protected MethodSecurityExpressionHandler createExpressionHandler() {
//    var expressionHandler =
//            new DefaultMethodSecurityExpressionHandler();
//
//    expressionHandler.setPermissionEvaluator(evaluator);
//
//    return expressionHandler;
//  }
}
