package dev.melvstein.money_games.app.game_apis.seamless.oneapi.controller;

import dev.melvstein.money_games.app.dto.response.ApiResponse;
import dev.melvstein.money_games.app.game_apis.seamless.oneapi.dto.GameLaunchParams;
import dev.melvstein.money_games.app.game_apis.seamless.oneapi.service.OneApiSeamlessGameApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/oneapi-seamless-game")
@RequiredArgsConstructor
public class OneApiSeamlessGameApiController {
    private final OneApiSeamlessGameApiService oneApiSeamlessGameApiService;

    @GetMapping("/game-launch-url/{gameProviderId}/{gameCode}/{gameMode}")
    public ResponseEntity<ApiResponse<String>> launchGame(
            @PathVariable Integer gameProviderId,
            @PathVariable String gameCode,
            @PathVariable String gameMode
    ) throws UnknownHostException {
        GameLaunchParams params = GameLaunchParams.builder()
                .gameProviderId(gameProviderId)
                .gameCode(gameCode)
                .gameMode(gameMode)
                .build();

        String launchUrl = oneApiSeamlessGameApiService.getGameLaunchUrl(params); // Placeholder URL

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .code("SUCCESS")
                        .message("Game launched successfully " + gameProviderId + " " + gameCode)
                        .data(launchUrl)
                        .build()
        );
    }
}
