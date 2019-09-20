package com.examples.games.rockpaperscissors.data;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoundResult {

    private Player winner;
    private boolean isDraw;
}
