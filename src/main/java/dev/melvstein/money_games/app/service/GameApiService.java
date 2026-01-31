package dev.melvstein.money_games.app.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.melvstein.money_games.app.dto.request.GameApiGetAllRequest;
import dev.melvstein.money_games.app.mapper.GameApiMapper;
import dev.melvstein.money_games.app.model.GameApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameApiService extends ServiceImpl<GameApiMapper, GameApi> {
    private final GameApiMapper gameApiMapper;
    public final static int STATUS_INACTIVE = 0;
    public final static int STATUS_ACTIVE = 1;
    public final static int STATUS_MAINTENANCE = 2;

    @Transactional(readOnly = true)
    public Page<GameApi> getAllGameApis(GameApiGetAllRequest request) {
        Page<GameApi> pageRequest = new Page<>(request.page(), request.size());
        boolean isAsc = !"desc".equalsIgnoreCase(request.sortOrder());

        LambdaQueryWrapper<GameApi> queryWrapper = new LambdaQueryWrapper<GameApi>()
                .eq(request.status() != null, GameApi::getStatus, request.status())
                .eq(request.isSeamless() != null, GameApi::getIsSeamless, request.isSeamless())
                .orderBy(
                        true,
                        isAsc,
                        switch (request.sortBy()) {
                            case "gameProviderId" -> GameApi::getGameProviderId;
                            case "displayName" -> GameApi::getDisplayName;
                            case "status" -> GameApi::getStatus;
                            case "isSeamless" -> GameApi::getIsSeamless;
                            case "updatedAt" -> GameApi::getUpdatedAt;
                            default -> GameApi::getCreatedAt;
                        });

        return page(pageRequest, queryWrapper);
    }

    @Transactional(readOnly = true)
    public List<GameApi> getActiveGameApis() {
        return list(new LambdaQueryWrapper<GameApi>()
                .eq(GameApi::getStatus, STATUS_ACTIVE)
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public GameApi addGameApi(GameApi gameApi) {
        save(gameApi);
        return gameApi;
    }

    @Transactional(rollbackFor = Exception.class)
    public GameApi updateGameApi(GameApi gameApi) {
        updateById(gameApi);
        return gameApi;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteGameApi(BigInteger id) {
        return removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByGameProviderId(Integer gameProviderId) {
        return remove(new LambdaQueryWrapper<GameApi>()
                .eq(GameApi::getGameProviderId, gameProviderId));
    }

    @Transactional(readOnly = true)
    public GameApi getGameApiById(BigInteger id) {
        return getById(id);
    }

    @Transactional(readOnly = true)
    public GameApi getGameApiByGameProviderId(Integer gameProviderId) {
        return getOne(new LambdaQueryWrapper<GameApi>()
                .eq(GameApi::getGameProviderId, gameProviderId));
    }
}
