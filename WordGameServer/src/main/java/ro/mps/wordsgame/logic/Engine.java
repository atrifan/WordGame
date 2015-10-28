package ro.mps.wordsgame.logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by atrifan on 10/28/2015.
 */
public class Engine {

    private HashMap<String, Player> players = new HashMap<String, Player>();
    private ArrayList<String> usedWords = new ArrayList<String>();
    private static Engine _instance = null;

    public synchronized static Engine getInstance() {
        if(_instance == null) {
            _instance = new Engine();
        }

        return _instance;
    }

    private Engine(){}
    /**
     * Should return true or false depending if the player could be registered or not
     * @param name
     * @return
     */
    public Player registerPlayer(String name) {
        //TODO:
        Player player = new Player();
        player.setName(name);
        player.setScor(0);
        players.put(name, player);
        return player;
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
