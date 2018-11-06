package org.jacpfx.webflux.saga.trip;

import java.util.Collections;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarBookApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	CarRepository repository;

	@Test
	public void testCreateTweet() {
		Car hotel = new Car("Tesla Model S P100D");

		webTestClient.post().uri("/car")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(hotel), Car.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.model").isEqualTo("Tesla Model S P100D");
	}

	@Test
	public void testGetSingleTweet() {
		Car car = repository.save( new Car("Tesla Model S P100D")).block();

		webTestClient.get()
				.uri("/car/{id}", Collections.singletonMap("id", car.getId()))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

}
