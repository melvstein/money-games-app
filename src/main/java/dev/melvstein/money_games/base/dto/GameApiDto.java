package dev.melvstein.money_games.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

@Builder
public record GameApiDto(
        BigInteger id,
        Integer gameApiId,
        String gameApiName,
        Integer status,
        Boolean isSeamless,
        Map<String, Object> extraInfo,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        Instant createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        Instant updatedAt
) {
}
