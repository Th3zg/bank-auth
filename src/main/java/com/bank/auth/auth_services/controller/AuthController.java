package com.bank.auth.auth_services.controller;

import com.bank.auth.auth_services.dto.ErrorResponse;
import com.bank.auth.auth_services.dto.LoginRequest;
import com.bank.auth.auth_services.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody LoginRequest request,
        HttpServletRequest httpRequest
    ) {
        return authService
            .login(request.username(), request.password())
            .fold(ResponseEntity::ok, failure -> {
                HttpStatus status = failure
                    .getPrimaryErrorCode()
                    .map(code ->
                        switch (code) {
                            case
                                INVALID_CREDENTIALS,
                                USER_NOT_FOUND -> HttpStatus.UNAUTHORIZED;
                            case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
                            default -> HttpStatus.BAD_REQUEST;
                        }
                    )
                    .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

                ErrorResponse<?> body = ErrorResponse.from(
                    status.value(),
                    status.getReasonPhrase(),
                    failure.getPrimaryErrorCode().get(),
                    failure.getUniqueErrorSet(),
                    httpRequest.getRequestURI()
                );
                return ResponseEntity.status(status).body(body);
            });
    }

    @CrossOrigin
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return null;
    }

    @CrossOrigin
    @PreAuthorize("")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh() {
        return null;
    }

    @CrossOrigin
    @PostMapping("/roles")
    public ResponseEntity<?> roles(@RequestBody LoginRequest request) {
        return null;
    }

    @CrossOrigin
    @PreAuthorize("") // los PreAuthorize deben ir en la capa de servicios
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody LoginRequest request) {
        return null;
    }

    @CrossOrigin
    @PostMapping("/introspect")
    public ResponseEntity<?> introspect() {
        return null;
    }

    @CrossOrigin
    @PutMapping("/password")
    public ResponseEntity<?> createPassword() {
        return null;
    }

    @CrossOrigin
    @PostMapping("/password/forgot")
    public ResponseEntity<?> forgotPassword() {
        return null;
    }

    @CrossOrigin
    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword() {
        return null;
    }

    @CrossOrigin
    @PutMapping("/mfa/enroll")
    public ResponseEntity<?> mfaEnroll() {
        return null;
    }

    @CrossOrigin
    @PutMapping("/mfa/verify")
    public ResponseEntity<?> mfaVerify() {
        return null;
    }

    @GetMapping("/.well-known/openid-configuration")
    public ResponseEntity<?> wellKnow() {
        return null;
    }

    @PostMapping("/roles/{roleId}/users/{userId}")
    public ResponseEntity<?> assingRole() {
        return null;
    }

    @DeleteMapping("/roles/{roleId}/users/{userId}")
    public ResponseEntity<?> deleteRole() {
        return null;
    }

    @GetMapping("/users/{userId}/permissions")
    public ResponseEntity<?> deleteRole() {
        return null;
    }
}
