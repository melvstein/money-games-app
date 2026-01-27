package dev.melvstein.money_games.base.controller;

import dev.melvstein.money_games.base.Helper.Util;
import dev.melvstein.money_games.base.dto.GameApiDto;
import dev.melvstein.money_games.base.dto.request.GameApiAddRequest;
import dev.melvstein.money_games.base.dto.response.ApiResponse;
import dev.melvstein.money_games.base.enums.ApiResponseCode;
import dev.melvstein.money_games.base.mapper.converter.GameApiConverter;
import dev.melvstein.money_games.base.model.GameApi;
import dev.melvstein.money_games.base.service.GameApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game_apis")
@RequiredArgsConstructor
public class GameApiController {
    private final GameApiService gameApiService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GameApiDto>>> getAllGameApis() {
        List<GameApi> gameApis = gameApiService.getAllGameApis();

        return ResponseEntity.ok(
                ApiResponse.<List<GameApiDto>>builder()
                        .code(ApiResponseCode.SUCCESS.getCode())
                        .message("Fetch all game APIs successfully")
                        .data(GameApiConverter.toDtos(gameApis))
                        .build()
        );
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<GameApiDto>>> getGameApis() {
        List<GameApi> gameApis = gameApiService.getActiveGameApis();

        return ResponseEntity.ok(
                ApiResponse.<List<GameApiDto>>builder()
                        .code(ApiResponseCode.SUCCESS.getCode())
                        .message("Fetch active game APIs successfully")
                        .data(GameApiConverter.toDtos(gameApis))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GameApiDto>> addGameApi(
            @RequestBody @Valid GameApiAddRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            // Collect all validation error messages
            String message = bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(
                    ApiResponse.<GameApiDto>builder()
                            .code(ApiResponseCode.BAD_REQUEST.getCode())
                            .message(message)
                            .build()
            );
        }

        try {
            GameApi savedGameApi = gameApiService.addGameApi(GameApiConverter.toModel(request));

            return ResponseEntity.ok(
                    ApiResponse.<GameApiDto>builder()
                            .code(ApiResponseCode.SUCCESS.getCode())
                            .message("Add new game API successfully")
                            .data(GameApiConverter.toDto(savedGameApi))
                            .build()
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<GameApiDto>builder()
                            .code(ApiResponseCode.BAD_REQUEST.getCode())
                            .message(e.getMessage())
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String message = Util.getRootCauseMessage(e);

            if (message.contains("Duplicate entry")) {
                status = HttpStatus.CONFLICT;
                message = "Record already exists";
            }

            return ResponseEntity
                    .status(status)
                    .body(
                            ApiResponse.<GameApiDto>builder()
                                    .code(ApiResponseCode.ERROR.getCode())
                                    .message(message)
                                    .build()
                    );
        }
    }
}
