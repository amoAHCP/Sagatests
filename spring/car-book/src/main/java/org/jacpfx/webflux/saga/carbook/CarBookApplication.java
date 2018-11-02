package org.jacpfx.webflux.saga.carbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class CarBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarBookApplication.class, args);
	}
}
