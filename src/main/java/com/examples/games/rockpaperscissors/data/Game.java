package com.examples.games.rockpaperscissors.data;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Game {

    @NotNull
    private String id;
    private List<Player> players;
    private RoundResult roundResult;

    public void setRoundResult(RoundResult roundResult) {
        this.roundResult = roundResult;
    }
}

