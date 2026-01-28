package dev.melvstein.money_games.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record GameApiAddRequest(
        @NotNull(message = "gameProviderId is required")
        Integer gameProviderId,

        @NotBlank(message = "gameProviderName is required")
        String gameProviderName,

        @NotBlank(message = "displayName is required")
        String displayName,

        @NotNull(message = "status is required")
        Integer status,

        @NotNull(message = "isSeamless is required")
        Boolean isSeamless,

        Map<String, Object> extraInfo
) {
}
