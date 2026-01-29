package dev.melvstein.money_games.app.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record GameApiGetAllRequest(
        @Min(1)
        Integer page,

        @Min(1)
        Integer size,

        String sortBy,
        String sortOrder,
        String status
) {
    public GameApiGetAllRequest {
        if (page == null || page < 1) {
            page = 1;
        }

        if (size == null || size < 1) {
            size = 10;
        }

        if  (sortBy == null || sortBy.isEmpty()) {
            sortBy = "createdAt";
        }

        if  (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "desc";
        }
    }
}
