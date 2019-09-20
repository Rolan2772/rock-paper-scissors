package com.examples.games.rockpaperscissors.engine;

import com.examples.games.rockpaperscissors.data.Game;
import com.examples.games.rockpaperscissors.data.Move;
import com.examples.games.rockpaperscissors.data.Player;
import com.examples.games.rockpaperscissors.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GameService {

    private final GameEngine gameEngine;
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameEngine gameEngine, GameRepository gameRepository) {
        this.gameEngine = gameEngine;
        this.gameRepository = gameRepository;
    }

    public Mono<Game> findGame(String id) {
        return gameRepository.findById(id);
    }

    public Mono<Game> handleMove(String gameId, int playerId, Move move) {
        return gameRepository.findById(gameId)
                .map(game -> {
                    Player player = findPlayer(game, playerId);
                    return gameEngine.makeMove(game, player, move);
                })
                .map(gameEngine::evaluateRound)
                .flatMap(gameRepository::save);
    }

    private Player findPlayer(Game game, int playerId) {
        return game.getPlayers()
                .stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Player id is not found: " + playerId));
    }

}
