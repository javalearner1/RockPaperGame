package com.couchgame.app;

import com.couchgame.app.dto.GameFinalResponse;
import com.couchgame.app.service.GameServiceImpl;
import com.couchgame.app.service.IGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Prabhakar.k
 * CouchbaseApplication is the main class which triggers the process of Game
 */
@SpringBootApplication(scanBasePackages = { "com.couchgame.app" })
@RestController
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class CouchbaseApplication {
	private static final Logger logger = LoggerFactory.getLogger(CouchbaseApplication.class);

	@Autowired
	public static IGameService service;
	public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		Instant start = Instant.now();
		SpringApplication.run(CouchbaseApplication.class, args);
		IGameService service = new GameServiceImpl();
		GameFinalResponse response =service.playGameAndReturnResult();
		response.setMessage(GameConstants.SUCCESS_MESSAGE);
		logger.info(GameConstants.SUCCESS_MESSAGE);
		service.persistResultToFile(response);
		Instant end = Instant.now();
		Long duration= Duration.between(start,end).getSeconds();
		logger.info("Total Time taken through main methods for 100 Rounds in seconds  -->"+duration);
	}

}



