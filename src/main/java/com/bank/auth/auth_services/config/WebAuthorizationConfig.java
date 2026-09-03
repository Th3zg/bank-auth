package com.bank.auth.auth_services.config;

import com.bank.auth.auth_services.jwt.filter.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebAuthorizationConfig {

    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(req ->
                req
                    .requestMatchers("/auth/login")
                    .permitAll()
                    .requestMatchers("/oauth2/**", "/.well-know/**")
                    .permitAll()
                    .requestMatchers("/auth/refresh", "/auth/loguot")
                    .authenticated()
                    .requestMatchers("/auth/mfa/**")
                    .authenticated()
                    .requestMatchers("/auth/roles/**")
                    .hasAuthority("ROLE:ASSING")
                    .requestMatchers("/auth/users/*/permissions")
                    .hasAnyAuthority("ROLE:READ", "USER:READ")
                    .anyRequest()
                    .denyAll()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s ->
                s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            )
            .exceptionHandling(ex ->
                ex.authenticationEntryPoint(
                    (request, response, authException) ->
                        response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            authException.getLocalizedMessage()
                        )
                )
            )
            .build();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(Collections.singletonList(provider));
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
        DataSource dataSource
    ) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
