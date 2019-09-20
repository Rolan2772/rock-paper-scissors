package com.examples.games.rockpaperscissors.engine;

import com.examples.games.rockpaperscissors.data.Game;
import com.examples.games.rockpaperscissors.data.Move;
import com.examples.games.rockpaperscissors.data.Player;
import com.examples.games.rockpaperscissors.data.RoundResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameEngineTest {

    private static final String GAME_ID = UUID.randomUUID().toString();
    private static final Integer PLAYER1_ID = 1;
    private static final Integer PLAYER2_ID = 2;

    @Mock
    private GameRules gameRules;

    @InjectMocks
    private GameEngine gameEngine;

    @Test
    public void makeMove() {
        Player player = Player.builder().id(PLAYER1_ID).build();
        Move move = Move.PAPER;
        Game game = Game.builder()
                .id(GAME_ID)
                .players(List.of(player))
                .build();

        Game actualGame = gameEngine.makeMove(game, player, move);

        assertEquals(game.getId(), actualGame.getId());
        Player actualPlayer = game.getPlayers().get(0);
        assertEquals(player.getId(), actualPlayer.getId());
        assertEquals(move, actualPlayer.getMove());
    }

    @Test
    public void evaluateGame() {
        Player player1 = Player.builder().id(PLAYER1_ID).move(Move.PAPER).build();
        Player player2 = Player.builder().id(PLAYER2_ID).build();
        Game game = Game.builder()
                .id(GAME_ID)
                .players(List.of(player1, player2))
                .build();

        Game actualGame = gameEngine.evaluateRound(game);

        assertEquals(game.getId(), actualGame.getId());
        assertNull(game.getRoundResult());
        verify(gameRules, times(0)).evaluateRound(any(), any());
    }

    @Test
    public void evaluateCompletedGame() {
        Player player1 = Player.builder().id(PLAYER1_ID).move(Move.PAPER).build();
        Player player2 = Player.builder().id(PLAYER2_ID).move(Move.PAPER).build();
        Game game = Game.builder()
                .id(UUID.randomUUID().toString())
                .players(List.of(player1, player2))
                .build();
        RoundResult roundResult = RoundResult.builder().isDraw(true).build();
        given(gameRules.evaluateRound(player1, player2)).willReturn(roundResult);

        Game actualGame = gameEngine.evaluateRound(game);

        assertEquals(game.getId(), actualGame.getId());
        assertEquals(roundResult, game.getRoundResult());
    }

}
