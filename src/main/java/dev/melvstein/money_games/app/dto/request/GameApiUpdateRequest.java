package dev.melvstein.money_games.app.dto.request;

import java.util.Map;

public record GameApiUpdateRequest(
        String displayName,
        Integer status,
        Boolean isSeamless,
        Map<String, Object> extraInfo
) {
}
