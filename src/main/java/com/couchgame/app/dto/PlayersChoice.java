package com.couchgame.app.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayersChoice {
    private String player1Choice;
    private String player2Choice;

    @Override
    public String toString() {
        return super.toString();
    }
}
