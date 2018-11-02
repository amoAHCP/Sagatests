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
public class FlightBookApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	FlightRepository repository;

	@Test
	public void testCreateFlight() {
		Flight hotel = new Flight("2017-10-01","BA286");

		webTestClient.post().uri("/flight")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(hotel), Flight.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.flightCode").isEqualTo("BA286");
	}

	@Test
	public void testGetSingleFlight() {
		Flight flight = repository.save( new Flight("2017-10-01","BA286")).block();

		webTestClient.get()
				.uri("/flight/{id}", Collections.singletonMap("id", flight.getId()))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

}
