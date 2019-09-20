package com.examples.games.rockpaperscissors.engine;

import com.examples.games.rockpaperscissors.data.Move;
import com.examples.games.rockpaperscissors.data.Player;
import com.examples.games.rockpaperscissors.data.RoundResult;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.AbstractMap;
import java.util.Map;

import static com.examples.games.rockpaperscissors.data.Move.*;

@Slf4j
@Component
public class GameRules {

    private static final Map<Combination, Result> RULES = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(ROCK).move2(ROCK).build(), Result.DRAW),
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(ROCK).move2(SCISSORS).build(), Result.WIN),
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(ROCK).move2(PAPER).build(), Result.LOSE),
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(SCISSORS).move2(ROCK).build(), Result.LOSE),
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(SCISSORS).move2(SCISSORS).build(), Result.DRAW),
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(SCISSORS).move2(PAPER).build(), Result.WIN),
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(PAPER).move2(ROCK).build(), Result.WIN),
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(PAPER).move2(SCISSORS).build(), Result.LOSE),
            new AbstractMap.SimpleEntry<>(Combination.builder().move1(PAPER).move2(PAPER).build(), Result.DRAW)
    );

    public RoundResult evaluateRound(Player player1, Player player2) {
        Combination combination = Combination.builder()
                .move1(player1.getMove())
                .move2(player2.getMove())
                .build();
        if (!RULES.containsKey(combination)) {
            throw new IllegalStateException("Unknown combination of moves: " + combination);
        }
        Result result = RULES.get(combination);
        log.debug("Combination {} evaluated with result {}", combination, result);
        return createRoundResult(player1, player2, result);

    }

    private RoundResult createRoundResult(Player player1, Player player2, Result result) {
        switch (result) {
            case DRAW:
                return RoundResult.builder().isDraw(true).build();
            case WIN:
                return RoundResult.builder().winner(player1).build();
            case LOSE:
                return RoundResult.builder().winner(player2).build();
            default:
                throw new IllegalStateException("Unknown result combination result: " + result);
        }
    }

    private enum Result {
        WIN, LOSE, DRAW;
    }

    @Value
    @Builder
    private static class Combination {
        @NotNull
        private Move move1;
        @NotNull
        private Move move2;
    }
}
