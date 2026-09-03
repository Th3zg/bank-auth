package com.bank.auth.auth_services.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    if (authException instanceof CompromisedPasswordException) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "The password was compromised");
      return;
    }

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
  }
}
