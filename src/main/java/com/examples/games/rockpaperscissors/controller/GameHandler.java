package com.examples.games.rockpaperscissors.controller;

import com.examples.games.rockpaperscissors.data.Game;
import com.examples.games.rockpaperscissors.data.Move;
import com.examples.games.rockpaperscissors.engine.GameService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class GameHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Mono<ServerResponse> findGame(ServerRequest request) {
        String id = request.pathVariable(GameRouter.GAME_ID);
        log.info("Searching for a game: {}", id);
        return gameService.findGame(id)
                .flatMap(game ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .syncBody(game));
    }

    public Mono<ServerResponse> createGame(ServerRequest request) {
        String id = UUID.randomUUID().toString();
        log.info("Creating new game {}", id);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(id), String.class);
    }

    public Mono<ServerResponse> handleMove(ServerRequest request) {
        String gameId = request.pathVariable(GameRouter.GAME_ID);
        Integer playerId = Integer.parseInt(request.pathVariable(GameRouter.PLAYER_ID));
        log.info("Handling game {} move for player {}", gameId, playerId);
        return request.bodyToMono(MoveBody.class)
                .map(MoveBody::getMove)
                .map(move -> gameService.handleMove(gameId, playerId, move))
                .flatMap(game ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(game, Game.class));
    }

    @Data
    @NoArgsConstructor
    public static class MoveBody {
        private Move move;
    }
}
