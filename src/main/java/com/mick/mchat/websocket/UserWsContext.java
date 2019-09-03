package com.mick.mchat.websocket;

import io.javalin.websocket.WsContext;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserWsContext {
    private final UUID userUuid;
    private final Map<String, WsContext> wsContexts;

    public UserWsContext(UUID userUuid) {
        this.userUuid = userUuid;
        wsContexts = new ConcurrentHashMap<>();
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public Map<String, WsContext> getWsContexts() {
        return wsContexts;
    }

    public void removeWsContext(String sessionId) {
        wsContexts.remove(sessionId);
    }

    public void addWsContext(WsContext wsContext) {
        wsContexts.put(wsContext.getSessionId(), wsContext);
    }
}
