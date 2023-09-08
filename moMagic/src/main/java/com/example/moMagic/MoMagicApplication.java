package com.example.moMagic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoMagicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoMagicApplication.class, args);
	}

}
