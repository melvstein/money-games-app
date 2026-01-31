package dev.melvstein.money_games.app.controller.rest_controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import dev.melvstein.money_games.app.Helper.Util;
import dev.melvstein.money_games.app.controller.BaseController;
import dev.melvstein.money_games.app.dto.GameApiDto;
import dev.melvstein.money_games.app.dto.request.GameApiAddRequest;
import dev.melvstein.money_games.app.dto.request.GameApiGetAllRequest;
import dev.melvstein.money_games.app.dto.request.GameApiUpdateRequest;
import dev.melvstein.money_games.app.dto.response.ApiResponse;
import dev.melvstein.money_games.app.enums.ApiResponseCode;
import dev.melvstein.money_games.app.mapper.converter.GameApiConverter;
import dev.melvstein.money_games.app.model.GameApi;
import dev.melvstein.money_games.app.service.GameApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/game_apis")
@RequiredArgsConstructor
public class GameApiController extends BaseController {
    private final GameApiService gameApiService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<GameApiDto>>> getAllGameApis(
            @ModelAttribute GameApiGetAllRequest request
    ) {
        Page<GameApi> gameApis = gameApiService.getAllGameApis(request);
        Page<GameApiDto> dtoPage = (Page<GameApiDto>) gameApis.convert(GameApiConverter::toDto);

        return ResponseEntity.ok(
                ApiResponse.<Page<GameApiDto>>builder()
                        .code(ApiResponseCode.SUCCESS.getCode())
                        .message("Fetch all game APIs successfully")
                        .data(dtoPage)
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
            @RequestBody @Valid GameApiAddRequest request
    ) {

        GameApi savedGameApi = gameApiService.addGameApi(GameApiConverter.toModel(request));

        return ResponseEntity.ok(
                ApiResponse.<GameApiDto>builder()
                        .code(ApiResponseCode.SUCCESS.getCode())
                        .message("Add new game API successfully")
                        .data(GameApiConverter.toDto(savedGameApi))
                        .build()
        );
    }

    @PatchMapping("/game-provider-id/{gameProviderId}")
    public ResponseEntity<ApiResponse<GameApiDto>> updateGameApi(
            @RequestBody @Valid GameApiUpdateRequest request,
            @PathVariable Integer gameProviderId
    ) {
        /*ResponseEntity<ApiResponse<GameApiDto>> requestValidation = requestValidation(bindingResult);

        if (requestValidation != null) {
            return requestValidation;
        }*/

        // check if game API exists
        GameApi existingGameApi = gameApiService.getGameApiByGameProviderId(gameProviderId);

        if  (existingGameApi == null) {
            ApiResponse<GameApiDto> response = ApiResponse.<GameApiDto>builder()
                    .code(ApiResponseCode.NOT_FOUND.getCode())
                    .message("Game provider ID " + gameProviderId + " not found")
                    .build();

            Map<String, Object> logResult = new HashMap<>();
            logResult.put("GameProviderId", gameProviderId);
            logResult.put("request", request);
            logResult.put("response", response);

            log.error("{} - logResult: {}", Util.getClassAndMethod(), objectMapper.writeValueAsString(logResult));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (request.displayName() != null) {
            existingGameApi.setDisplayName(request.displayName());
        }

        if (request.status() != null) {
            existingGameApi.setStatus(request.status());
        }

        if (request.isSeamless() != null) {
            existingGameApi.setIsSeamless(request.isSeamless());
        }

        if (request.extraInfo() != null) {
            existingGameApi.setExtraInfo(request.extraInfo());
        }

        GameApi updatedGameApi = gameApiService.updateGameApi(existingGameApi);

        return ResponseEntity.ok(
                ApiResponse.<GameApiDto>builder()
                        .code(ApiResponseCode.SUCCESS.getCode())
                        .message("Update game API successfully")
                        .data(GameApiConverter.toDto(updatedGameApi))
                        .build()
        );
    }

    @DeleteMapping("/game-provider-id/{gameProviderId}")
    public ResponseEntity<ApiResponse<Void>> deleteByGameProviderId(
            @PathVariable Integer gameProviderId
    ) {
        boolean deleted = gameApiService.deleteByGameProviderId(gameProviderId);

        if (!deleted) {
            log.error("{} - GameProviderId {} not found", Util.getClassAndMethod(), gameProviderId);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.<Void>builder()
                                    .code(ApiResponseCode.NOT_FOUND.getCode())
                                    .message("Game Provider ID " + gameProviderId + " not found")
                                    .build()
                    );
        }

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(ApiResponseCode.SUCCESS.getCode())
                        .message("Delete game API successfully")
                        .build()
        );
    }
}
