package com.mick.mchat.websocket.model;

/**
 * Base class for all websocket messages, lets do some polymorphic deserialization yehawww
 * */
public class WebsocketMessage<T extends MessageBody> {
    private MessageInfo info;
    private T body;

    public MessageInfo getInfo() {
        return info;
    }

    public WebsocketMessage<T> setInfo(MessageInfo info) {
        this.info = info;
        return this;
    }

    public T getBody() {
        return body;
    }

    public WebsocketMessage<T> setBody(T body) {
        this.body = body;
        return this;
    }
}
