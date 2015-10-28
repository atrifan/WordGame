package ro.mps.wordsgame.logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by atrifan on 10/28/2015.
 */
public class Engine {

    private HashMap<String, Player> players = new HashMap<String, Player>();
    private ArrayList<String> usedWords = new ArrayList<String>();

    /**
     * Should return true or false depending if the player could be registered or not
     * @param name
     * @return
     */
    public boolean registerPlayer(String name) {
        //TODO:
        return true;
    }

    /**
     * Should return true false if the player was removed
     * @param name
     * @return
     */
    public boolean removePlayer(String name) {
        //TODO:
        return true;
    }

    public String getLetters() {
        //TODO:
        return null;
    }

    public ArrayList<Player> getPlayers() {
        //TODO:
        return null;
    }

    public long getScore(String word) {
        //TODO:
        return 0;
    }

    public Player getPlayer(String name) {
        //TODO:
        return null;
    }

    public ArrayList<String> getUsedWords(){
        return usedWords;
    }
}
