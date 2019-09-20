package com.examples.games.rockpaperscissors.engine;

import com.examples.games.rockpaperscissors.data.Game;
import com.examples.games.rockpaperscissors.data.Move;
import com.examples.games.rockpaperscissors.data.Player;
import com.examples.games.rockpaperscissors.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    private static final String GAME_ID = UUID.randomUUID().toString();
    private static final Integer PLAYER1_ID = 1;
    private static final Integer PLAYER2_ID = 2;

    @Mock
    private GameEngine gameEngine;
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private Player player1 = Player.builder().id(PLAYER1_ID).build();
    private Player player2 = Player.builder().id(PLAYER2_ID).build();
    private Game game = Game.builder()
            .id(GAME_ID)
            .players(Stream.of(player1, player2).collect(Collectors.toList()))
            .build();

    @Before
    public void before() {
        given(gameRepository.findById(GAME_ID)).willReturn(Mono.just(game));
        given(gameRepository.save(game)).willReturn(Mono.just(game));
        given(gameEngine.evaluateRound(game)).willReturn(game);
    }

    @Test
    public void findGame() {
        gameService.findGame(GAME_ID);

        Mockito.verify(gameRepository).findById(GAME_ID);
    }

    @Test
    public void handleMove() {
        Move move = Move.PAPER;
        given(gameEngine.makeMove(game, player1, move)).willReturn(game);

        gameService.handleMove(GAME_ID, PLAYER1_ID, move).block();

        assertEquals(GAME_ID, game.getId());
        verify(gameRepository).findById(GAME_ID);
        verify(gameEngine).makeMove(game, game.getPlayers().get(0), move);
        verify(gameEngine).evaluateRound(game);
        verify(gameRepository).save(game);
    }

    @Test
    public void completeRound() {
        Move move1 = Move.PAPER;
        Move move2 = Move.SCISSORS;
        given(gameEngine.makeMove(game, player1, move1)).willReturn(game);
        given(gameEngine.makeMove(game, player2, move2)).willReturn(game);

        gameService.handleMove(GAME_ID, PLAYER1_ID, move1).block();
        gameService.handleMove(GAME_ID, PLAYER2_ID, move2).block();

        assertEquals(GAME_ID, game.getId());
        verify(gameRepository, times(2)).findById(GAME_ID);
        verify(gameEngine).makeMove(game, player1, move1);
        verify(gameEngine).makeMove(game, player2, move2);
        verify(gameEngine, times(2)).evaluateRound(game);
        verify(gameRepository, times(2)).save(game);
    }
}
