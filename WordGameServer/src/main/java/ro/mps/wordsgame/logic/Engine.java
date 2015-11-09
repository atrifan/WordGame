package ro.mps.wordsgame.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import ro.mps.wordsgame.model.EVENT;
import ro.mps.wordsgame.model.WsMessage;
import ro.mps.wordsgame.servlet.WsServlet;

/**
 * Created by atrifan on 10/28/2015.
 */
public class Engine {

	private static final int LETTERS_BROADCAST_TIME = 30;

	private static final String dices[] = {
			"AAUIHJ",
			"TRNSMB",
			"AARCDM",
			"EEIODF",
			"AEUSFV",
			"TLNPGC",
			"AIOEXZ",
			"NSTRGB",
			"IIUELP"
	};
	
    private HashMap<String, Player> players = new HashMap<String, Player>();
    private ArrayList<String> usedWords = new ArrayList<String>();
    private String letters = null;
    private Timer timer = null;
    private ILanguage language = new RomanianLanguage();
    private Dictionary dictionary = new Dictionary();
    private static Engine _instance = null;

    public synchronized static Engine getInstance() {
        if(_instance == null) {
            _instance = new Engine();
        }
        return _instance;
    }

    private Engine() {
    	timer = new Timer();
    	timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (letters != null) {
					WsMessage lettersBroadcastMessage = new WsMessage();
					lettersBroadcastMessage.setEvent(EVENT.lettersBroadcast);
					lettersBroadcastMessage.setData(letters);
					try {
						WsServlet.broadcast(lettersBroadcastMessage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
    		
    	}, LETTERS_BROADCAST_TIME);
    }
    
    /**
     * Should return true or false depending if the player could be registered or not
     * @param name
     * @return
     */
    public Player registerPlayer(String name) throws IOException {
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
    
    private String[] generateDices() {
    	return dices;
    }

    public String getLetters() {
    	String[] dices = generateDices();
    	StringBuffer letters = new StringBuffer();
    	Random random = new Random();
    	for (String dice : dices) {
    		letters.append(dice.charAt(random.nextInt(dice.length())));
    	}
    	return letters.toString();
    }

    public ArrayList<Player> getPlayers() {
        Set<String> playerNames = players.keySet();
        ArrayList<Player> response = new ArrayList<Player>();
        for(String userName : playerNames) {
            response.add(players.get(userName));
        }
        return response;
    }
    
    /**
     * 
     * @param word
     * @param letters
     * @return true if word is composed of letters (taking special characters into account)
     */
    private boolean isComposedOfLetters(String word, String letters) {
    	if (word == null || letters == null)
    		return false;
    	HashMap<Character, String> mappings = language.getMappings();
    	for (int i = 0; i < word.length(); ++i) {
    		boolean isValidLetter = false;
    		if (mappings.containsKey(word.charAt(i))) {
    			String mapping = mappings.get(word.charAt(i));
    			for (int j = 0; j < mapping.length(); j++) {
    				if (letters.indexOf(mapping.charAt(i)) != -1) {
    					isValidLetter = true;
    					break;
    				}
    			}
    		} else {
    			isValidLetter = (letters.indexOf(word.charAt(i)) != -1);
    		}
    		if (!isValidLetter) {
    			return false;
    		}
    	}
    	return true;
    }

    public long getScore(String word) {
    	if (!isComposedOfLetters(word, letters) ||
    			!dictionary.isValid(word)) {
    		return 0;
    	}
    	if (!usedWords.contains(word)) {
    		usedWords.add(word);
    	}
    	return word.length();
    }

    public Player getPlayer(String name) {
    	return players.get(name);
    }

    public ArrayList<String> getUsedWords(){
        return usedWords;
    }
}
