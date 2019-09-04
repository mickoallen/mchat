package com.mick.mchat.websocket;

import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserWsContext {
    private static final Logger logger = LoggerFactory.getLogger(UserWsContext.class);

    private final UUID userUuid;
    private final Map<String, WsContext> wsContexts = new ConcurrentHashMap<>();

    public UserWsContext(UUID userUuid) {
        this.userUuid = userUuid;
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
