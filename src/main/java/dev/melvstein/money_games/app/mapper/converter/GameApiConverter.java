package dev.melvstein.money_games.app.mapper.converter;

import dev.melvstein.money_games.app.dto.GameApiDto;
import dev.melvstein.money_games.app.dto.request.GameApiAddRequest;
import dev.melvstein.money_games.app.dto.request.GameApiUpdateRequest;
import dev.melvstein.money_games.app.model.GameApi;

import java.util.List;

public class GameApiConverter {

    public static GameApiDto toDto(GameApi gameApi) {
        if (gameApi == null) {
            return null;
        }

        return GameApiDto.builder()
                .id(gameApi.getId())
                .gameProviderId(gameApi.getGameProviderId())
                .gameProviderName(gameApi.getGameProviderName())
                .displayName(gameApi.getDisplayName())
                .status(gameApi.getStatus())
                .isSeamless(gameApi.getIsSeamless())
                .extraInfo(gameApi.getExtraInfo())
                .createdAt(gameApi.getCreatedAt())
                .updatedAt(gameApi.getUpdatedAt())
                .build();
    }

    public static GameApi toModel(GameApiDto dto) {
        if (dto == null) {
            return null;
        }

        return GameApi.builder()
                .id(dto.id())
                .gameProviderId(dto.gameProviderId())
                .gameProviderName(dto.gameProviderName())
                .displayName(dto.displayName())
                .status(dto.status())
                .isSeamless(dto.isSeamless())
                .extraInfo(dto.extraInfo())
                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt())
                .build();
    }

    public static GameApi toModel(GameApiAddRequest request) {
        if (request == null) {
            return null;
        }

        return GameApi.builder()
                .gameProviderId(request.gameProviderId())
                .gameProviderName(request.gameProviderName())
                .displayName(request.displayName())
                .status(request.status())
                .isSeamless(request.isSeamless())
                .extraInfo(request.extraInfo())
                .build();
    }

    public static GameApi toModel(GameApiUpdateRequest request) {
        if (request == null) {
            return null;
        }

        return GameApi.builder()
                .displayName(request.displayName())
                .status(request.status())
                .isSeamless(request.isSeamless())
                .extraInfo(request.extraInfo())
                .build();
    }

    public static List<GameApiDto> toDtos(List<GameApi> gameApis) {
        return gameApis.stream()
                .map(GameApiConverter::toDto)
                .toList();
    }
}
