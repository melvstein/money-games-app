package dev.melvstein.money_games.base.controller;

import dev.melvstein.money_games.base.model.GameApi;
import dev.melvstein.money_games.base.service.GameApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game_apis")
@RequiredArgsConstructor
public class GameApiController {
    private final GameApiService gameApiService;

    @GetMapping
    public ResponseEntity<List<GameApi>> getGameApis() {
        List<GameApi> gameApis = gameApiService.getActiveGameApis();
        return ResponseEntity.ok(gameApis);
    }
}
