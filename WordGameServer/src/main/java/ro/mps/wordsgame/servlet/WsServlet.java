package ro.mps.wordsgame.servlet;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import ro.mps.wordsgame.controller.PlayerController;
import ro.mps.wordsgame.logic.Player;
import ro.mps.wordsgame.model.WsMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by atrifan on 10/28/2015.
 */
public class WsServlet extends WebSocketServlet {
    private static final long serialVersionUID = 1L;
    private static ConcurrentHashMap<String, PlayerController> sockets = new ConcurrentHashMap<String, PlayerController>();
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static void addConnection(String name, PlayerController controller) {
        sockets.put(name, controller);
    }

    public static PlayerController getConnection(String name) {
        return sockets.get(name);
    }

    public static void removeConnection(String name) {
        sockets.remove(name);
    }

    public static void broadcast(WsMessage message) throws IOException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        Set<String> keys = sockets.keySet();
        for(String user : keys) {
            WsOutbound outbound = sockets.get(user).getWsOutbound();
            outbound.writeTextMessage(CharBuffer.wrap(jsonMessage));
        }
    }

    public static void sendToUser(String userName, WsMessage message) throws IOException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        if(sockets.containsKey(userName)) {
            WsOutbound outbound = sockets.get(userName).getWsOutbound();
            outbound.writeTextMessage(CharBuffer.wrap(jsonMessage));
        }

    }

    @Override
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest httpServletRequest) {
        return new PlayerController();
    }
}
