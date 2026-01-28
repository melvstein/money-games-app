package dev.melvstein.money_games.app.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Map;

@TableName(value = "game_apis", autoResultMap = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameApi {
    @TableId(type = IdType.AUTO)
    private BigInteger id;

    @TableField("game_provider_id")
    private Integer gameProviderId;

    @TableField("game_provider_name")
    private String gameProviderName;

    @TableField("display_name")
    private  String displayName;

    private Integer status;

    @TableField("is_seamless")
    private Boolean isSeamless;

    @TableField(value = "extra_info", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> extraInfo;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Instant createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Instant updatedAt;
}
