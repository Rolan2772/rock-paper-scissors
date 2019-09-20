package com.examples.games.rockpaperscissors.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class GameRouter {

    public static final String GAME_ID = "game";
    public static final String PLAYER_ID = "player";

    @Bean
    public RouterFunction<ServerResponse> gameRoute(GameHandler gameHandler) {
        return route(GET("/start"), gameHandler::createGame)
                .andNest(path("/game"),
                        route(POST("/{" + GAME_ID + "}/{" + PLAYER_ID + "}"), gameHandler::handleMove)
                                .andRoute(GET("/{" + GAME_ID + "}"), gameHandler::findGame));
    }
}
