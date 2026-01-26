package dev.melvstein.money_games.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@TableName("users")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String firstName;
    private String middleName;
    private  String lastName;
    private String email;
    private String username;
    private String password;
    private String passwordHash;
    private Instant createdAt;
    private Instant updatedAt;
}
