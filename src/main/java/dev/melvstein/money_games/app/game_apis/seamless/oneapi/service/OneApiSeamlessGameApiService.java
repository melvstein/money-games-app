package dev.melvstein.money_games.app.game_apis.seamless.oneapi.service;

import dev.melvstein.money_games.app.Helper.Util;
import dev.melvstein.money_games.app.enums.GameApiEnum;
import dev.melvstein.money_games.app.game_apis.seamless.oneapi.dto.GameLaunchParams;
import dev.melvstein.money_games.app.game_apis.seamless.oneapi.dto.GameLaunchResponse;
import dev.melvstein.money_games.app.model.GameApi;
import dev.melvstein.money_games.app.service.AbstractGameService;
import dev.melvstein.money_games.app.service.GameApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OneApiSeamlessGameApiService extends AbstractGameService {
    private final GameApiService gameApiService;
    private static String GAME_LAUNCH_URL_ENDPOINT = "/game/url";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public String getGameLaunchUrl(GameLaunchParams params) throws UnknownHostException {
        try {
            GameApi gameApi = gameApiService.getGameApiByGameProviderId(GameApiEnum.ONEAPI_SEAMLESS_GAME_API.getGameProviderId());
            Map<String, Object> extraInfo = gameApi.getExtraInfo();
            InetAddress ip = InetAddress.getLocalHost();
            String url = extraInfo.get("apiUrl") + GAME_LAUNCH_URL_ENDPOINT;
            String secretKey = (String) extraInfo.get("secretKey");
            String lobbyUrl = (String) extraInfo.get("lobbyUrl");

            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("username", "mdbstestt1dev");
            requestParams.put("traceId", UUID.randomUUID().toString());
            requestParams.put("gameCode", params.gameCode());
            requestParams.put("language", "en");
            requestParams.put("platform", "web");
            requestParams.put("currency", "BRL");
            requestParams.put("lobbyUrl", lobbyUrl);
            requestParams.put("ipAddress", ip.getHostAddress());

            if (params.gameMode().equalsIgnoreCase("trial")) {
                requestParams = new HashMap<>();
            }

            String requestBody = objectMapper.writeValueAsString(requestParams);


            log.info("getGameLaunchUrl requestBody: {}", requestBody);
            log.info("getGameLaunchUrl secretKey: {}", secretKey);
            String signature = Util.generateHmacSHA256Hex(requestBody, secretKey);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", (String) extraInfo.get("apiKey"));
            headers.set("X-Signature", signature);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<GameLaunchResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, GameLaunchResponse.class);

            log.info("getGameLaunchUrl extraInfo: {}", extraInfo);
            log.info("getGameLaunchUrl response: {}", objectMapper.writeValueAsString(response));

            if (response.getBody() != null && response.getBody().data() != null) {
                return response.getBody().data().gameUrl();
            }

            throw new RuntimeException("Game URL not found in response");
        } catch (Exception e) {
            log.error("{} - Failed to request game URL", Util.getClassAndMethod(), e);
            throw new RuntimeException("Game API request failed", e);
        }
    }
}
