package com.couchgame.app.service;

import com.couchgame.app.GameConstants;
import com.couchgame.app.dto.GameFinalResponse;
import com.couchgame.app.dto.PlayersChoice;
import com.couchgame.app.dto.RoundResult;
import com.couchgame.app.threads.PlayerOneNextMove;
import com.couchgame.app.threads.PlayerTwoNextMove;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * @Author:Prabhakar.k
 * This class is added for handling execution  of Rock,Paper,Scissor game
 * It handles the core logic of intiating Threads,generating result and persisting it
 */

@Service
public class GameServiceImpl implements IGameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private static Map<Integer, String> gameOptionsMap= new HashMap<>();
    public static volatile List<RoundResult> roundResultList=new ArrayList<>();
    private static ExecutorService executor = null;
    private volatile static Future<Integer> player1Choice=null;
    private volatile static Future<Integer> player2Choice=null;

    static{
        gameOptionsMap.put(1, GameConstants.ROCK);
        gameOptionsMap.put(2,GameConstants.PAPER);
        gameOptionsMap.put(3,GameConstants.SCISSORS);
    }

    /**
     * createPlayersNextMove() generates the next move of both players
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void createPlayersNextMove() throws ExecutionException, InterruptedException{
        if (player1Choice == null
                || player1Choice.isDone()
                || player1Choice.isCancelled())
        {
            player1Choice = executor.submit(new PlayerOneNextMove(roundResultList));
            logger.info("player1Choice "+player1Choice.get());
        }
        if (player2Choice == null
                || player2Choice.isDone()
                || player2Choice.isCancelled())
        {
            player2Choice = executor.submit(new PlayerTwoNextMove(roundResultList,gameOptionsMap));
            logger.info("player2Choice "+player2Choice.get());
        }
    }

    /**
     * playGameAndReturnResult() used for handling the core logic and executing the game for 100 rounds
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public GameFinalResponse playGameAndReturnResult() throws ExecutionException, InterruptedException, IOException {
        executor = Executors.newFixedThreadPool(2);
        int counter=1;

        GameFinalResponse finalResponse = new GameFinalResponse();
        PlayersChoice playersChoice = null;
        RoundResult roundResult= null;
        while (counter<=100) {
            logger.info("----- ROUND ----"+counter);
            createPlayersNextMove();
            playersChoice = new PlayersChoice();
            roundResult = new RoundResult();
            if ((gameOptionsMap.containsKey(player1Choice.get()) && player1Choice.get()==1)) {
                if (player2Choice.get()==1) {
                    logger.info("Result is tie");
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.TIE);
                }else if(player2Choice.get()==2){
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.PLAYER_2_WINNER);
                    logger.info("Player 2 is winner");
                }else{
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.PLAYER_1_WINNER);
                    logger.info("Player 1 is winner");
                }
            }else if((player1Choice.get())==2){
                if (player2Choice.get()==1) {
                    logger.info("Player 1 is winner");
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.PLAYER_1_WINNER);
                }else if(player2Choice.get()==2){
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.TIE);
                    logger.info("Tie");
                }else{
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.PLAYER_2_WINNER);
                    logger.info("Player 2 is winner");
                }
            }else if(player1Choice.get()==3){
                if (player2Choice.get()==1) {
                    logger.info("Player 2 is winner");
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.PLAYER_2_WINNER);
                }else if(player2Choice.get()==2){
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.PLAYER_1_WINNER);
                    logger.info("winner Player 1");
                }else{
                    roundResult.setRound(counter);
                    roundResult.setWinner(GameConstants.TIE);
                    logger.info("Result is Tie");

                }
            }
            playersChoice.setPlayer1Choice(gameOptionsMap.get(player1Choice.get()));
            playersChoice.setPlayer2Choice(gameOptionsMap.get(player2Choice.get()));
            roundResult.setPlayerInputs(playersChoice);
            roundResultList.add(roundResult);
            finalResponse.setFinalResponse(roundResultList);
            ++counter;
        }
        finalResponse.setFinalResponse(roundResultList);
        return finalResponse;
    }

    /**
     * persistResultToFile()- Persists the Game result  to a file in JSON Format
     * @param response
     * @return
     * @throws IOException
     */
    public boolean persistResultToFile(GameFinalResponse response) throws IOException {
        if(response!=null){
        String json = new Gson().toJson(response );
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        String finalGameResponse = gson.toJson(element);
        logger.info("Final Response after 100 Rounds in JSON Format "+finalGameResponse);
        FileWriter file = new FileWriter("RockGame_Response.json");
        file.write(finalGameResponse);
        file.close();
        return true;
        }else {
            return false;
        }
    }

    }
