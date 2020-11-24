package com.couchgame.app.restapi;

import com.couchgame.app.GameConstants;
import com.couchgame.app.dto.GameFinalResponse;
import com.couchgame.app.CouchbaseApplication;
import com.couchgame.app.service.GameServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import java.time.Instant;

/**
 * @Author : Prabhakar
 * GameController represents the http end point  to start the game
 */
@RestController
@RequestMapping("/api/games")
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(CouchbaseApplication.class);
    @Autowired
    private GameServiceImpl service;

    @RequestMapping(value="/game", method= RequestMethod.GET)
    public ResponseEntity<GameFinalResponse> playAndReturnGameResult()   {
        Instant start = Instant.now();
        GameFinalResponse gameFinalResponse=null;
        try {
            gameFinalResponse =service.playGameAndReturnResult();
            service.persistResultToFile(gameFinalResponse);
            gameFinalResponse.setMessage(GameConstants.SUCCESS_MESSAGE);
        } catch (Exception e) {
            return  new ResponseEntity<>(gameFinalResponse, HttpStatus.EXPECTATION_FAILED);
        }finally {

        }
        Instant end = Instant.now();
        Long duration= Duration.between(start,end).getSeconds();
        logger.info("Total Time taken through Rest service methods for 100 Rounds -->"+duration);
        return new ResponseEntity<>(gameFinalResponse, HttpStatus.OK);

    }
}

