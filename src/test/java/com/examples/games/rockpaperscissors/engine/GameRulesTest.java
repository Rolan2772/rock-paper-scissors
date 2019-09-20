package com.examples.games.rockpaperscissors.engine;

import com.examples.games.rockpaperscissors.data.Move;
import com.examples.games.rockpaperscissors.data.Player;
import com.examples.games.rockpaperscissors.data.RoundResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameRulesTest {

    @InjectMocks
    private GameRules gameRules;

    @Test
    public void drawCombinations() {
        Player.PlayerBuilder player1 = Player.builder().id(1);
        Player.PlayerBuilder player2 = Player.builder().id(2);

        RoundResult rockDraw = gameRules.evaluateRound(player1.move(Move.ROCK).build(), player2.move(Move.ROCK).build());
        RoundResult paperDraw = gameRules.evaluateRound(player1.move(Move.PAPER).build(), player2.move(Move.PAPER).build());
        RoundResult scissorsDraw = gameRules.evaluateRound(player1.move(Move.SCISSORS).build(), player2.move(Move.SCISSORS).build());

        assertDraw(paperDraw);
        assertDraw(rockDraw);
        assertDraw(scissorsDraw);
    }

    private void assertDraw(RoundResult roundResult) {
        assertNull(roundResult.getWinner());
        assertTrue(roundResult.isDraw());
    }

    @Test
    public void winCombinations() {
        Integer winnerId = 1;
        Player.PlayerBuilder player1 = Player.builder().id(winnerId);
        Player.PlayerBuilder player2 = Player.builder().id(2);

        RoundResult rockScissors = gameRules.evaluateRound(player1.move(Move.ROCK).build(), player2.move(Move.SCISSORS).build());
        RoundResult paperRock = gameRules.evaluateRound(player1.move(Move.PAPER).build(), player2.move(Move.ROCK).build());
        RoundResult scissorsPaper = gameRules.evaluateRound(player1.move(Move.SCISSORS).build(), player2.move(Move.PAPER).build());


        assertWin(rockScissors, winnerId);
        assertWin(paperRock, winnerId);
        assertWin(scissorsPaper, winnerId);
    }

    private void assertWin(RoundResult roundResult, Integer winnerId) {
        assertNotNull(roundResult.getWinner());
        assertEquals(winnerId, roundResult.getWinner().getId());
        assertFalse(roundResult.isDraw());
    }

    @Test
    public void looseCombinations() {
        Integer winnerId = 2;
        Player.PlayerBuilder player1 = Player.builder().id(1);
        Player.PlayerBuilder player2 = Player.builder().id(winnerId);

        RoundResult rockPaper = gameRules.evaluateRound(player1.move(Move.ROCK).build(), player2.move(Move.PAPER).build());
        RoundResult paperScissors = gameRules.evaluateRound(player1.move(Move.PAPER).build(), player2.move(Move.SCISSORS).build());
        RoundResult scissorsRock = gameRules.evaluateRound(player1.move(Move.SCISSORS).build(), player2.move(Move.ROCK).build());


        assertWin(rockPaper, winnerId);
        assertWin(paperScissors, winnerId);
        assertWin(scissorsRock, winnerId);
    }


}
