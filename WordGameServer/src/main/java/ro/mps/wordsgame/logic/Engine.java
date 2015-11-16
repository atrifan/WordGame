package ro.mps.wordsgame.logic;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import ro.mps.wordsgame.model.EVENT;
import ro.mps.wordsgame.model.WsMessage;
import ro.mps.wordsgame.servlet.WsServlet;

/**
 * Created by atrifan on 10/28/2015.
 */
public class Engine {

    private static final int LETTERS_BROADCAST_TIME = 60000;

    private static final String dices[] = { "AAUIHJ", "TRNSMB", "AARCDM", "EEIODF", "AEUSFV", "TLNPGC", "AIOEXZ",
            "NSTRGB", "IIUELP" };

    private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<String, Player>();
    private ArrayList<String> usedWords = new ArrayList<String>();
    private String letters = null;
    private Timer timer = null;
    private ILanguage language = new RomanianLanguage();
    private Dictionary dictionary;
    private static Engine _instance = null;

    public synchronized static Engine getInstance() throws Exception {
        if (_instance == null) {
            _instance = new Engine();
        }
        return _instance;
    }

    private Engine() throws Exception {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                letters = generateLetters();
                usedWords.clear();
                WsMessage lettersBroadcastMessage = new WsMessage();
                lettersBroadcastMessage.setEvent(EVENT.lettersBroadcast);
                lettersBroadcastMessage.setData(letters);
                Collection<Player> thePlayers = players.values();
                for(Player player : thePlayers) {
                    player.setScor(0);
                    updatePlayer(player.getName(), player);
                    WsServlet.updatePlayerController(player);
                }
                try {
                    WsServlet.broadcast(lettersBroadcastMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }, 0, LETTERS_BROADCAST_TIME);
        dictionary = new Dictionary();
    }

    /**
     * Should return true or false depending if the player could be registered
     * or not
     * 
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
     * 
     * @param name
     * @return
     */
    public boolean removePlayer(String name) throws IOException {
        if (players.containsKey(name)) {
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
        return letters;
    }

    private String generateLetters() {
        String[] dices = generateDices();
        StringBuffer letters = new StringBuffer();
        Random random = new Random();
        for (String dice : dices) {
            letters.append(dice.charAt(random.nextInt(dice.length())));
        }
        return letters.toString();
    }

    public ArrayList<Player> getPlayers() {
        Collection<Player> playerEntities = players.values();
        ArrayList<Player> response = new ArrayList<Player>();
        for (Player playerEntity : playerEntities) {
            response.add(playerEntity);
        }
        return response;
    }

    public synchronized void updatePlayer(String name, Player player) {
        players.put(name, player);
    }
    /**
     * 
     * @param word
     * @param letters
     * @return true if word is composed of letters (taking special characters
     *         into account)
     */
    private boolean isComposedOfLetters(String word, String letters) {
        if (word == null || letters == null) {
            System.out.println("DA FUQ");
            return false;
        }

        for(int i = 0; i < word.length(); i++) {
            if(letters.indexOf(word.charAt(i)) == -1) {
                System.out.println("FOUND IT HERE " + word.charAt(i) + "---" + letters);
                return false;
            };
        }
        return true;
        //THE BELOW CODE IS FAULTY AND DOES NOT WORK AS EXPECTED
        /*
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
        return true;*/
    }

    public long getScore(String word) throws IOException {
        if (!isComposedOfLetters(word.toLowerCase(), letters.toLowerCase()) || !dictionary.isValid(word.toLowerCase())) {
            return 0;
        }

        System.out.println("OK HERE");
        if (!usedWords.contains(word.toLowerCase())) {
            usedWords.add(word.toLowerCase());
            WsMessage usedWordMessage = new WsMessage();
            usedWordMessage.setEvent(EVENT.usedWord);
            usedWordMessage.setData(word);
            WsServlet.broadcast(usedWordMessage);
            return word.length();
        }

        return 0;
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }

    public ArrayList<String> getUsedWords() {
        return usedWords;
    }
}
