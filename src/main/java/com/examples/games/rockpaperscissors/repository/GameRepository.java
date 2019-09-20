package com.examples.games.rockpaperscissors.repository;

import com.examples.games.rockpaperscissors.data.Game;
import reactor.core.publisher.Mono;

public interface GameRepository {

    Mono<Game> findById(String id);

    Mono<Game> save(Game game);
}
