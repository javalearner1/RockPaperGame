package com.couchgame.app.service;

import com.couchgame.app.dto.GameFinalResponse;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface IGameService {

     GameFinalResponse playGameAndReturnResult() throws ExecutionException, InterruptedException, IOException;
     boolean persistResultToFile(GameFinalResponse response) throws IOException;
}
