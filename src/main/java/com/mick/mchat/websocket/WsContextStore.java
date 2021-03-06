package com.mick.mchat.websocket;

import com.mick.mchat.websocket.outbound.OutMessageWrapper;
import io.javalin.websocket.WsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class WsContextStore {
    private static final Logger logger = LoggerFactory.getLogger(WsContextStore.class);

    /* <User UUIDs, Their respective ws connection> */
    private final Map<UUID, UserWsContext> userWsContextMap;

    @Inject
    public WsContextStore() {
        this.userWsContextMap = new ConcurrentHashMap<>();
    }

    public List<WsContext> getWsContextForUsers(Collection<UUID> userUuids) {
        List<UserWsContext> userWsContexts = userUuids.stream()
                .map(userWsContextMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        logger.info("user contexts for user: {}, {}", userUuids, userWsContexts.toArray());

        List<WsContext> wsContexts = new ArrayList<>();
        for (UserWsContext wsContext : userWsContexts) {
            if(wsContext == null){
                continue;
            }else if(wsContext.getWsContexts() == null){
                throw new RuntimeException("user sessionWsContext map is null");
            }
            for (Map.Entry<String, WsContext> sessionWsContextEntry : wsContext.getWsContexts().entrySet()) {
                if(sessionWsContextEntry.getValue() == null){
                    logger.warn("Null connection detected - removing reference");
                    wsContext.removeWsContext(sessionWsContextEntry.getKey());
                }else {
                    wsContexts.add(sessionWsContextEntry.getValue());
                }
            }
        }

        return wsContexts;
    }

    public void addUserWsContext(UUID userUuid, WsContext wsContext) {
        UserWsContext userWsContext;
        if(userWsContextMap.containsKey(userUuid)){
            logger.warn("user already has an existing websocket context, adding connection");
            userWsContext = userWsContextMap.get(userUuid);
        }else{
            userWsContext = new UserWsContext(userUuid);
        }
        userWsContext.addWsContext(wsContext);
        userWsContextMap.put(userUuid, userWsContext);
    }

    public Map<UUID, UserWsContext> getContextMap() {
        return userWsContextMap;
    }

    public void sendMessageToAllUsers(OutMessageWrapper outMessageWrapper) {
        userWsContextMap.values()
                .stream()
                .flatMap(userWsContext -> userWsContext.getWsContexts().values().stream())
                .filter(Objects::nonNull)
                .forEach(wsContext -> wsContext.send(outMessageWrapper));
    }

    public boolean userIsOnline(UUID uuid) {
        if(!userWsContextMap.containsKey(uuid)){
            return false;
        }

        return userWsContextMap.get(uuid)
                .getWsContexts()
                .values()
                .stream()
                .anyMatch(Objects::nonNull);
    }

    public void removeUserWsContextForSessionId(String sessionId) {
        userWsContextMap.values()
                .forEach(userWsContext -> userWsContext.removeWsContext(sessionId));
    }

    public void removeUserWsContextForUserUUID(UUID userUuid) {
        userWsContextMap.remove(userUuid);
    }
}
