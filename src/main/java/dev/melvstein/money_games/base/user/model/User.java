package dev.melvstein.money_games.base.user.model;

import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@Data
public class User {
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
