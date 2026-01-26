package dev.melvstein.money_games.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.melvstein.money_games.base.model.GameApi;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameApiMapper extends BaseMapper<GameApi> {
}
