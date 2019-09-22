package com.examples.games.rockpaperscissors.repository;

import com.examples.games.rockpaperscissors.data.Game;
import com.examples.games.rockpaperscissors.data.Player;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryGameRepository implements GameRepository {

    private Map<String, Game> games = new ConcurrentHashMap<>();

    @Override
    public Mono<Game> findById(String id) {
        return Mono.just(games.getOrDefault(id,
                Game.builder()
                        .id(id)
                        .players(Stream.of(
                                Player.builder().id(1).build(),
                                Player.builder().id(2).build())
                                .collect(Collectors.toList()))
                        .build()));
    }

    @Override
    public Mono<Game> save(@NotNull Game game) {
        games.put(game.getId(), game);
        return Mono.just(game);
    }
}
