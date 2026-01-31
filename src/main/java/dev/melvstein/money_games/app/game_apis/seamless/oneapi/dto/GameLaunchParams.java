package dev.melvstein.money_games.app.game_apis.seamless.oneapi.dto;

import lombok.Builder;

@Builder
public record GameLaunchParams(
        Integer gameProviderId,
        String gameCode,
        String gameMode
) {
}
