package dev.melvstein.money_games.base.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.Instant;

@TableName("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String role;

    @TableField("first_name")
    private String firstName;

    @TableField("middle_name")
    private String middleName;

    @TableField("last_name")
    private String lastName;

    private String email;

    private String username;

    private String password;

    @TableField("password_hash")
    private String passwordHash;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Instant createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Instant updatedAt;
}
