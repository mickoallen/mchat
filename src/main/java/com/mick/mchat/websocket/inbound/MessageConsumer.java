package com.mick.mchat.websocket.inbound;

import com.mick.mchat.security.AuthenticationToken;
import io.javalin.websocket.WsContext;

@FunctionalInterface
public interface MessageConsumer {
    void accept(InMessage inMessage, AuthenticationToken authenticationToken, WsContext wsContext) throws Exception;
}
