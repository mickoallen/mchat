package com.mick.mchat.websocket;

import io.javalin.websocket.WsContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class WsContextStore {
    private final Map<UUID, WsContext> userWsContexts;

    @Inject
    public WsContextStore() {
        this.userWsContexts = new ConcurrentHashMap<>();
    }

    public List<WsContext> getWsContextForUsers(Collection<UUID> userUuids){
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

    public Collection<WsContext> getAllWsContexts() {
        return userWsContexts.values();
    }
}
