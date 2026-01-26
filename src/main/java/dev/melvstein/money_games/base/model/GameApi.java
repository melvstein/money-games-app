package dev.melvstein.money_games.base.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@TableName("game_apis")
@Data
public class GameApi {
    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String gameApiName;
    private int status;
    private boolean is_seamless;
    private String extraInfo;
    private Instant createdAt;
    private Instant updatedAt;
}
