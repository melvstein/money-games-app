package dev.melvstein.money_games.app.exception.handler;

import dev.melvstein.money_games.app.Helper.Util;
import dev.melvstein.money_games.app.dto.response.ApiResponse;
import dev.melvstein.money_games.app.enums.ApiResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    /* ===============================
       Validation (@Valid) errors
       =============================== */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Validation failed");

        String requestUri = request.getRequestURI();  // /api/game/1
        String queryString = request.getQueryString(); // ?param=value
        Map<String, String[]> params = request.getParameterMap(); // all query/form params

        Map<String, Object> logResult = new HashMap<>();
        logResult.put("uri", requestUri);
        logResult.put("query", queryString);
        logResult.put("params", params);

        log.info("{} - logResult: {}", Util.getClassAndMethod(), objectMapper.writeValueAsString(logResult));
        log.error("{} - error_message: {}", Util.getClassAndMethod(), message, ex);

        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .code(ApiResponseCode.BAD_REQUEST.getCode())
                        .message(message)
                        .build()
        );
    }

    /* ===============================
       JSON parse errors
       =============================== */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleJsonParse(HttpMessageNotReadableException ex) {
        log.error("{} - error_message: {}", Util.getClassAndMethod(), ex.getMessage(), ex);

        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .code(ApiResponseCode.BAD_REQUEST.getCode())
                        .message("Invalid JSON request body")
                        .build()
        );
    }

    /* ===============================
       Illegal arguments
       =============================== */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("{} - error_message: {}", Util.getClassAndMethod(), ex.getMessage(), ex);

        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .code(ApiResponseCode.BAD_REQUEST.getCode())
                        .message(ex.getMessage())
                        .build()
        );
    }

    /* ===============================
       DB constraint errors
       =============================== */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String message = Util.getRootCauseMessage(ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (message.contains("Duplicate entry")) {
            status = HttpStatus.CONFLICT;
            message = "Record already exists";
        }

        log.error("{} - error_message: {}", Util.getClassAndMethod(), message, ex);

        return ResponseEntity.status(status).body(
                ApiResponse.<Void>builder()
                        .code(ApiResponseCode.ERROR.getCode())
                        .message(message)
                        .build()
        );
    }

    /* ===============================
       Fallback (500)
       =============================== */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        log.error("{} - error_message: {}", Util.getClassAndMethod(), ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.<Void>builder()
                        .code(ApiResponseCode.ERROR.getCode())
                        .message("Internal server error")
                        .build()
        );
    }
}
