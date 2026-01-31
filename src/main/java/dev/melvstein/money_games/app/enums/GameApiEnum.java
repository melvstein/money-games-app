package dev.melvstein.money_games.app.enums;

import lombok.*;

@Getter
@AllArgsConstructor
public enum GameApiEnum {
    ONEAPI_SEAMLESS_GAME_API(1000, "ONEAPI_SEAMLESS_GAME_API");

    private final Integer gameProviderId;
    private final String gameProviderName;
}
