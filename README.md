# Getting Started

### Reference Documentation
This Application is about playing Rock ,Paper,Scissor game

It creates 2 seperate threads representing 2 players ,each thread is responsbile to generate next move for its player

Second player move is decided based on previous move of the First player ,when first player moves are empty on application instantiation ,Thread generates a random move for player 2

This game is exposed as Rest service  and  standalone application aswell

http://localhost:8080/api/games/game

application is stopped when Game reaches 100 rounds

Response is printed in logs and persisted to RockGame_Response.json file 
which will saved to your local file system/Project current directory

Junits
1 unit test case has been written in CouchbaseApplicationTests.java which verifies if the response has 100 round results
and it verfied the JSON format of the response.

