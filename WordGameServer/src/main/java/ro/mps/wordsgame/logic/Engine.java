package ro.mps.wordsgame.logic;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import ro.mps.wordsgame.model.EVENT;
import ro.mps.wordsgame.model.WsMessage;
import ro.mps.wordsgame.servlet.WsServlet;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
    public Player registerPlayer(String name) throws IOException {
        //TODO:
        Player player = new Player();
        player.setName(name);
        player.setScor(0);
        players.put(name, player);

        WsMessage responseMessage = new WsMessage();
        responseMessage.setEvent(EVENT.joinedUser);
        responseMessage.setData(player);
        WsServlet.broadcast(responseMessage);
        return player;
    }

    /**
     * Should return true false if the player was removed
     * @param name
     * @return
     */
    public boolean removePlayer(String name) throws IOException {
        //TODO:

        if(players.containsKey(name)) {
            Player playerToRemove = players.get(name);
            players.remove(name);
            WsMessage responseMessage = new WsMessage();
            responseMessage.setEvent(EVENT.leftUser);
            responseMessage.setData(playerToRemove);
            WsServlet.broadcast(responseMessage);
            return true;
        }

        return false;
    }

    public String getLetters() {
        //TODO:
        return null;
    }

    public ArrayList<Player> getPlayers() {
        //TODO:
        Set<String> playerNames = players.keySet();
        ArrayList<Player> response = new ArrayList<Player>();
        for(String userName : playerNames) {
            response.add(players.get(userName));
        }
        return response;
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
