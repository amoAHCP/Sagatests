package org.jacpfx.webflux.saga.trip;

import java.util.UUID;
import org.jacpfx.webflux.saga.trip.model.Car;
import org.jacpfx.webflux.saga.trip.model.Car.CarBuilder;
import org.junit.Ignore;
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
public class TripAggregateApplicationTests {

  @Autowired private WebTestClient webTestClient;

  @Test
  @Ignore
  public void testCreateTweet() {
    final String transactionId = UUID.randomUUID().toString();
    Car hotel =
        new CarBuilder()
            .setModel("Tesla Model S P100D")
            .setTransactionId(transactionId)
            .createCar();

    webTestClient
        .post()
        .uri("/car")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .body(Mono.just(hotel), Car.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody()
        .jsonPath("$.id")
        .isNotEmpty()
        .jsonPath("$.model")
        .isEqualTo("Tesla Model S P100D");
  }
}
