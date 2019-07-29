package com.mick.mchat.websocket.model;

/**
 * Base class for all websocket messages, lets do some polymorphic deserialization yehawww
 * */
public class WebsocketMessage<T extends MessageBody> {
    private MessageContext context;
    private T body;

    public MessageContext getContext() {
        return context;
    }

    public WebsocketMessage<T> setContext(MessageContext context) {
        this.context = context;
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
