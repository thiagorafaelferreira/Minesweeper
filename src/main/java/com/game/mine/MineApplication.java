package com.game.mine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class MineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MineApplication.class, args);
	}

}
