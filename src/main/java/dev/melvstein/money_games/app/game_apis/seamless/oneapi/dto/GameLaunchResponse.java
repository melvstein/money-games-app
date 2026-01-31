package dev.melvstein.money_games.app.game_apis.seamless.oneapi.dto;

import java.util.Map;

public record GameLaunchResponse(
        String status,
        String traceId,
        Data data
) {
    public record Data(
            String gameUrl,
            String token
    ){

    }
}
