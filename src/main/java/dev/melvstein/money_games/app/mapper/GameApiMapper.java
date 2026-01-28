package dev.melvstein.money_games.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.melvstein.money_games.app.model.GameApi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameApiMapper extends BaseMapper<GameApi> {
    List<GameApi> selectAllGameApis();
}
