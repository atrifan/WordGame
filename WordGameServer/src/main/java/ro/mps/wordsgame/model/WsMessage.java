package ro.mps.wordsgame.model;

import java.io.Serializable;

/**
 * Created by atrifan on 10/28/2015.
 */
public class WsMessage implements Serializable{

    private EVENT event;
    private Object data;

    public EVENT getEvent() {
        return event;
    }

    public void setEvent(EVENT event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
