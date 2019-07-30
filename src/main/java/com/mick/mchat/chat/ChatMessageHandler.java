package com.mick.mchat.chat;

import com.mick.mchat.websocket.inbound.InboundMessage;
import com.mick.mchat.websocket.inbound.InboundMessageHandler;
import com.mick.mchat.websocket.inbound.InboundMessageType;
import com.mick.mchat.websocket.outbound.OutboundMessage;
import io.javalin.websocket.WsContext;
import kotlin.jvm.functions.Function2;

import java.util.Map;
import java.util.Optional;

public class ChatMessageHandler implements InboundMessageHandler {
    @Override
    public Map<InboundMessageType, Function2<? extends InboundMessage, WsContext, Optional<? extends OutboundMessage>>> getHandlerMap() {
        return Map.of();
    }
}
