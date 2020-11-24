package com.couchgame.app.threads;

import com.couchgame.app.dto.RoundResult;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**@Author :Prabhakar k
 * PlayerOneNextMove class responsible for generating First player move and return by a thread
 */
public class PlayerTwoNextMove implements Callable<Integer> {
    Random random1 = new Random();
    List<RoundResult> roundResultList;
    Map<Integer,String> actionMap;
    public PlayerTwoNextMove(List<RoundResult> roundResultList,Map<Integer,String> actionMap){
        this.roundResultList=roundResultList;
        this.actionMap=actionMap;
    }

    /**
     * It generates teh next move of the first player and returns
     * @return Integer
     */
    @Override
    public Integer call() {
        int player2random=0;
        if(roundResultList.size()==0){
            player2random = 1 + random1.nextInt(3);
            System.out.println("Reading Player 2 Choice is -->"+player2random);
            return player2random;
        }else {
            String player2Choice=roundResultList.get(roundResultList.size()-1).getPlayerInputs().getPlayer1Choice();
            for(Map.Entry<Integer,String> entry :actionMap.entrySet()){
                if(player2Choice.equalsIgnoreCase(entry.getValue())){
                    player2random=entry.getKey();
                }
            }
            System.out.println("Reading player 2 choice from player1 previous choice -->"+player2Choice);
            return player2random;
        }

    }
}
