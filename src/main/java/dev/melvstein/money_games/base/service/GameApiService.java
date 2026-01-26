package dev.melvstein.money_games.base.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import dev.melvstein.money_games.base.mapper.GameApiMapper;
import dev.melvstein.money_games.base.model.GameApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameApiService {
    private final GameApiMapper gameApiMapper;
    public final static int STATUS_INACTIVE = 0;
    public final static int STATUS_ACTIVE = 1;
    public final static int STATUS_MAINTENANCE = 2;

    public List<GameApi> getActiveGameApis() {
        return gameApiMapper.selectList(
                new LambdaQueryWrapper<GameApi>()
                        .eq(GameApi::getStatus, STATUS_ACTIVE)
        );
    }
}
