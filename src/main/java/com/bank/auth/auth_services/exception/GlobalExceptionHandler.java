package com.bank.auth.auth_services.exception;

import com.bank.auth.auth_services.dto.ErrorResponse;
import com.bank.auth.auth_services.enums.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // Validation errors (e.g., @Valid)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse<AuthErrorCode>> handleValidationErrors(MethodArgumentNotValidException ex) {
    Set<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .collect(Collectors.toSet());

    return ResponseEntity.badRequest()
            .body(ErrorResponse.from(
                    AuthErrorCode.FIELD_VALIDATION_FAILED.getStatus(),
                    AuthErrorCode.FIELD_VALIDATION_FAILED.getMessage(),
                    AuthErrorCode.FIELD_VALIDATION_FAILED,
                    errors,
                    null
            ));
  }

  // Database errors
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponse<AuthErrorCode>> handleDatabaseError(DataAccessException ex) {
    logger.error("Database error occurred", ex);

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(ErrorResponse.from(
                    HttpStatus.SERVICE_UNAVAILABLE.value(),
                    AuthErrorCode.DATABASE_ERROR.getMessage(),
                    AuthErrorCode.DATABASE_ERROR,
                    "Service unavailable, please try again later")
            );
  }

  // Any other uncontrolled error
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse<AuthErrorCode>> handleUnexpectedError(Exception ex) {
    logger.error("Unexpected error occurred", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.from(
                    AuthErrorCode.INTERNAL_ERROR.getStatus(),
                    AuthErrorCode.INTERNAL_ERROR.getMessage(),
                    AuthErrorCode.INTERNAL_ERROR,
                    ("An unexpected error occurred")
            ));
  }
}

