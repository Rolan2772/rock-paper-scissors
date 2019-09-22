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
        if (game.getRoundResult() != null) {
            log.trace("Removing game {} round results.", game);
            game.setRoundResult(null);
            game.getPlayers().forEach(Player::clearMove);
        }

        if (player.getMove() != move) {
            player.setMove(move);
        }
        return game;
    }

    public Game evaluateRound(Game game) {
        if (isRoundComplete(game)) {
            log.trace("Evaluating game {} round.", game);
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
