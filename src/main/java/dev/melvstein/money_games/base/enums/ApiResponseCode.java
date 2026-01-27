package dev.melvstein.money_games.base.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {
    SUCCESS("SUCCESS", "Success"),
    ERROR("ERROR", "Internal Server Error"),
    NOT_FOUND("NOT_FOUND", "Resource Not Found"),
    BAD_REQUEST("BAD_REQUEST", "Bad Request");

    private final String code;
    private final String message;
}
