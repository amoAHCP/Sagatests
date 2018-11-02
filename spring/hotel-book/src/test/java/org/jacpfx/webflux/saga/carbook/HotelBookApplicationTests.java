package org.jacpfx.webflux.saga.carbook;

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
public class HotelBookApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	HotelRepository repository;

	@Test
	public void testCreateHotel() {
		Hotel hotel = new Hotel("SF","Hilton");

		webTestClient.post().uri("/hotel")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(hotel), Hotel.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.hotel").isEqualTo("Hilton");
	}

	@Test
	public void testGetSingleHotel() {
		Hotel hotel = repository.save( new Hotel("SF","Hilton")).block();

		webTestClient.get()
				.uri("/hotel/{id}", Collections.singletonMap("id", hotel.getId()))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

}
