package com.mick.mchat.websocket;

import io.javalin.websocket.WsContext;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class WsContextStore {
    private final Map<UUID, WsContext> userWsContexts;

    public WsContextStore() {
        this.userWsContexts = new ConcurrentHashMap<>();
    }

    public List<WsContext> getWsContextForUsers(List<UUID> userUuids){
        return userUuids.stream()
                .map(userWsContexts::get)
                .collect(Collectors.toList());
    }

    public void addUserWsContext(UUID userUuid, WsContext wsContext){
        userWsContexts.put(userUuid, wsContext);
    }

    public void removeUserWsContext(UUID userUuid){
        userWsContexts.remove(userUuid);
    }
}
