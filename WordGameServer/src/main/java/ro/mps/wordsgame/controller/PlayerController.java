package ro.mps.wordsgame.controller;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import ro.mps.wordsgame.logic.Engine;
import ro.mps.wordsgame.logic.Player;
import ro.mps.wordsgame.model.EVENT;
import ro.mps.wordsgame.model.WsMessage;
import ro.mps.wordsgame.servlet.WsServlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by atrifan on 10/28/2015.
 */
public class PlayerController extends MessageInbound {
    WsOutbound wsOutbound;
    private ObjectMapper objectMapper = new ObjectMapper();
    Engine gameEngine = Engine.getInstance();
    Player myPlayer = null;

    @Override
    public void onOpen(WsOutbound outbound) {
        try {
            System.out.println("Open Client.");
            this.wsOutbound = outbound;
            outbound.writeTextMessage(CharBuffer.wrap("Hello!"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onClose(int status) {
        try {
            System.out.println(String.format("Client %s closed", this.myPlayer.getName()));
            String name = this.myPlayer.getName();
            WsServlet.removeConnection(name);
            gameEngine.removePlayer(name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {

    }

    @Override
    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        System.out.println("Accept Message : "+ charBuffer);
        WsMessage message = objectMapper.readValue(charBuffer.toString(), WsMessage.class);
        EVENT event = message.getEvent();
        switch (event) {
            case register:
                this._registerPlayer(message);
                break;
        }
    }

    protected void _registerPlayer(WsMessage message) throws IOException {
        String name = (String) message.getData();
        WsServlet.addConnection(name, this);
        myPlayer = gameEngine.registerPlayer(name);
        WsMessage responseMessage = new WsMessage();
        responseMessage.setEvent(EVENT.registerSelf);
        responseMessage.setData(true);
        String jsonMessage = objectMapper.writeValueAsString(responseMessage);
        this.wsOutbound.writeTextMessage(CharBuffer.wrap(jsonMessage));
    }
}
