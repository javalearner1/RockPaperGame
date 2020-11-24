package com.couchgame.app.service;

import com.couchgame.app.dto.GameFinalResponse;
import com.couchgame.app.restapi.GameController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author:Prabhakar.k
 * This class is added for test cases of Rock,Paper,Scissor game
 */
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class CouchbaseApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private GameController gameController;


	/**
	 * Below test case verifies if the game has completed 100 Rounds and validates the response JSON
	 */
	@Test
	public void  testplayandReturnGameResult()  {
		//Response ccan be mocked for testing since its not a DB interaction for simplicity invoking the actual method
		ResponseEntity<GameFinalResponse> response =gameController.playAndReturnGameResult();
		Assertions.assertEquals(response.getStatusCodeValue(),200);
		Assertions.assertEquals(100,response.getBody().getFinalResponse().size());

		String json = new Gson().toJson(response.getBody().getFinalResponse());
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		Assertions.assertEquals(true,element.isJsonArray());
	}


}
