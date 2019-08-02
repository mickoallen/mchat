package com.mick.mchat;

import com.mick.mchat.websocket.MessageCodec;
import com.mick.mchat.websocket.WsContextStore;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HealthHandler {

    private final WsContextStore wsContextStore;

    @Inject
    public HealthHandler(WsContextStore wsContextStore) {
        this.wsContextStore = wsContextStore;
    }

    public String getHealth(){
        return "We are ok!";
    }

    public String getConnectedUsers(){
        return MessageCodec.serialize(wsContextStore.getContextMap().keySet());
    }
}
