package com.couchgame.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GameFinalResponse {
    private String message;
    private List<RoundResult> finalResponse;


    @Override
    public String toString() {
        return super.toString();
    }
}
