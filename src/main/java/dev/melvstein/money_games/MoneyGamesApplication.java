package dev.melvstein.money_games;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("dev.melvstein.money_games.base.mapper")
public class MoneyGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyGamesApplication.class, args);
	}

}
