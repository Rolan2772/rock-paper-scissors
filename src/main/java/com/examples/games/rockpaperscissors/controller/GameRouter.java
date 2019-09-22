package com.examples.games.rockpaperscissors.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class GameRouter {

    public static final String GAME_ID = "game";
    public static final String PLAYER_ID = "player";

    @Bean
    public RouterFunction<ServerResponse> gameApiRoute(GameHandler gameHandler) {
        return nest(path("/game"),
                route(GET("/create"), gameHandler::createGame)
                        .andRoute(POST("/{" + GAME_ID + "}/{" + PLAYER_ID + "}"), gameHandler::handleMove)
                        .andRoute(GET("/{" + GAME_ID + "}"), gameHandler::findGame));
    }

    @Bean
    public RouterFunction<ServerResponse> gameUiRoute(@Value("classpath:/public/index.html") final Resource indexHtml) {
        return route(GET("/game/{" + GAME_ID + "}/{" + PLAYER_ID + "}"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(indexHtml));
    }
}
