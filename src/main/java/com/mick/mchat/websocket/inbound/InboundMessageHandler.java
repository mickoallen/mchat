package com.mick.mchat.websocket.inbound;

import com.mick.mchat.websocket.outbound.OutboundMessage;
import io.javalin.websocket.WsContext;
import kotlin.jvm.functions.Function2;

import java.util.Map;
import java.util.Optional;

public interface InboundMessageHandler {
    Map<InboundMessageType, Function2<? extends InboundMessage, WsContext, Optional<? extends OutboundMessage>>> getHandlerMap();
}
