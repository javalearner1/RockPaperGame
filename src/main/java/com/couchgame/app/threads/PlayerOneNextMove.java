package com.couchgame.app.threads;

import com.couchgame.app.dto.RoundResult;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**@Author :Prabhakar k
 * PlayerOneNextMove class responsible for generating First player move and return by a thread
 */
public class  PlayerOneNextMove implements Callable<Integer> {
    Random random=new Random();
    List<RoundResult> roundResultList;
    public PlayerOneNextMove(List<RoundResult> roundResultList){
        this.roundResultList=roundResultList;
    }

    /**
     * It generates teh next move of the first player and returns
     * @return Integer
     */
    @Override
    public Integer call() {
        int player1random= 1+random.nextInt(3);
        return player1random;
    }
}
