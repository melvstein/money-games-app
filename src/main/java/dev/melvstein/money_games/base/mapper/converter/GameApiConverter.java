package dev.melvstein.money_games.base.mapper.converter;

import dev.melvstein.money_games.base.dto.GameApiDto;
import dev.melvstein.money_games.base.dto.request.GameApiAddRequest;
import dev.melvstein.money_games.base.model.GameApi;

import java.util.List;

public class GameApiConverter {

    public static GameApiDto toDto(GameApi gameApi) {
        if (gameApi == null) {
            return null;
        }

        return GameApiDto.builder()
                .id(gameApi.getId())
                .gameApiId(gameApi.getGameApiId())
                .gameApiName(gameApi.getGameApiName())
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
                .gameApiId(dto.gameApiId())
                .gameApiName(dto.gameApiName())
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
                .gameApiId(request.gameApiId())
                .gameApiName(request.gameApiName())
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
