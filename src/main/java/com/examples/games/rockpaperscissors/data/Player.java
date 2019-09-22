package com.examples.games.rockpaperscissors.data;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Player {

    @NotNull
    private Integer id;
    private Move move;

    public void clearMove() {
        this.move = null;
    }
}
