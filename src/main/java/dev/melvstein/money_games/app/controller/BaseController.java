package dev.melvstein.money_games.app.controller;

import dev.melvstein.money_games.app.dto.response.ApiResponse;
import dev.melvstein.money_games.app.enums.ApiResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BaseController {

    protected  <T> ResponseEntity<ApiResponse<T>> requestValidation(
            BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            return null;
        }

        String message = bindingResult
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Validation failed");

        return ResponseEntity.badRequest().body(
                ApiResponse.<T>builder()
                        .code(ApiResponseCode.BAD_REQUEST.getCode())
                        .message(message)
                        .build()
        );
    }
}
