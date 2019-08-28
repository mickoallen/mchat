package com.mick.mchat.websocket.inbound;

import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;
import io.javalin.websocket.WsContext;

@FunctionalInterface
public interface ReturningMessageConsumer {
    OutMessageWrapper accept(InMessage inMessage, AuthenticationToken authenticationToken, WsContext wsContext) throws Exception;
}
