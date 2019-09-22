package com.examples.games.rockpaperscissors.controller;

import com.examples.games.rockpaperscissors.data.Game;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameRoutingTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void create() {
        webTestClient
                .get().uri("/game/create")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class);
    }

    @Ignore
    @Test
    public void findGame() {
        webTestClient
                .get().uri("/game/{" + GameRouter.GAME_ID + "}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Game.class);
    }
}
