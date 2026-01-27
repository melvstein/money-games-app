package dev.melvstein.money_games.base.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.melvstein.money_games.base.mapper.GameApiMapper;
import dev.melvstein.money_games.base.model.GameApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameApiService extends ServiceImpl<GameApiMapper, GameApi> {
    private final GameApiMapper gameApiMapper;
    public final static int STATUS_INACTIVE = 0;
    public final static int STATUS_ACTIVE = 1;
    public final static int STATUS_MAINTENANCE = 2;

    @Transactional(readOnly = true)
    public List<GameApi> getAllGameApis() {
        return list(new LambdaQueryWrapper<GameApi>());
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
}
