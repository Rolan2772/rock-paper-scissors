package com.examples.games.rockpaperscissors.engine;

import com.examples.games.rockpaperscissors.data.Game;
import com.examples.games.rockpaperscissors.data.Move;
import com.examples.games.rockpaperscissors.data.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class GameEngine {

    private GameRules gameRules;

    @Autowired
    public GameEngine(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    public Game makeMove(Game game, Player player, Move move) {
        if (player.getMove() != null) {
            throw new IllegalStateException("Player already made a move: " + player);
        }
        player.setMove(move);
        return game;
    }

    public Game evaluateRound(Game game) {
        if (isRoundComplete(game)) {
            log.info("Evaluating game {} round.", game);
            Player player1 = game.getPlayers().get(0);
            Player player2 = game.getPlayers().get(1);
            game.setRoundResult(gameRules.evaluateRound(player1, player2));
        }
        return game;
    }

    private boolean isRoundComplete(Game game) {
        return game.getPlayers().stream()
                .map(Player::getMove)
                .filter(Objects::nonNull)
                .count() == 2;
    }
}
