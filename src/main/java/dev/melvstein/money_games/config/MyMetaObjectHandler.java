package dev.melvstein.money_games.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdAt", java.time.Instant.class, java.time.Instant.now());
        this.strictInsertFill(metaObject, "updatedAt", java.time.Instant.class, java.time.Instant.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updatedAt", java.time.Instant.class, java.time.Instant.now());
    }
}
