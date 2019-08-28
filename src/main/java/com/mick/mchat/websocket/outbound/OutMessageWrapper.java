package com.mick.mchat.websocket.outbound;

public class OutMessageWrapper {

    private final OutMessageType type;
    private Object body;

    public OutMessageWrapper(OutMessageType type) {
        this.type = type;
    }

    public static OutMessageWrapper of(OutMessageType type){
        return new OutMessageWrapper(type);
    }

    public OutMessageWrapper body(Object body){
        this.body = body;
        return this;
    }

    public OutMessageType getType() {
        return type;
    }

    public Object getBody() {
        return body;
    }
}
