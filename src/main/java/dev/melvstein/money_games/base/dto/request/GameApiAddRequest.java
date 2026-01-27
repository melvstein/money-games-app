package dev.melvstein.money_games.base.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record GameApiAddRequest(
        @NotNull(message = "gameApiId is required")
        Integer gameApiId,

        @NotBlank(message = "gameApiName is required")
        String gameApiName,

        @NotNull(message = "status is required")
        Integer status,

        @NotNull(message = "isSeamless is required")
        Boolean isSeamless,

        Map<String, Object> extraInfo
) {
}
