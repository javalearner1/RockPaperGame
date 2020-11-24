package com.couchgame.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoundResult {
    private int round;
    private String winner;
    private PlayersChoice playerInputs;

    @Override
    public String toString() {
        return super.toString();
    }
}
