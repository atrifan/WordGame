package ro.mps.wordsgame.servlet;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by atrifan on 10/28/2015.
 */
public class WsServlet extends WebSocketServlet {
    private static final long serialVersionUID = 1L;
    private static ConcurrentHashMap<String, PlayerController> sockets = new ConcurrentHashMap<String, PlayerController>();
    public static void addConnection(String name, PlayerController controller) {
        sockets.put(name, controller);
    }

    public static PlayerController getConnection(String name) {
        return sockets.get(name);
    }

    public static void removeConnection(String name) {
        sockets.remove(name);
    }

    @Override
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest httpServletRequest) {
        return new PlayerController();
    }
}
