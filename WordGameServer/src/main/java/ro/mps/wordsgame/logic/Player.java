package ro.mps.wordsgame.logic;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by atrifan on 10/28/2015.
 */
public class Player implements Serializable{

    private String name;
    private long scor;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScor() {
        return scor;
    }

    public void setScor(long scor) {
        this.scor = scor;
    }

    /**
     * Should return the score of the player's move
     * @param word
     * @return
     */
    public void play(String word) throws IOException {
        //TODO:
        long score = Engine.getInstance().getScore(word);
        this.scor += score;
    }
}
