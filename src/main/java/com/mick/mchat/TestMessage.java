package com.mick.mchat;

import io.javalin.websocket.WsContext;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TestMessage {
    public String doc;
    public Set<WsContext> clients;

    public TestMessage() {
        this.doc = "";
        this.clients = ConcurrentHashMap.newKeySet();
    }
}
