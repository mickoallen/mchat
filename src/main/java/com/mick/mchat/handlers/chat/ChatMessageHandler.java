package com.mick.mchat.handlers.chat;

import com.mick.mchat.handlers.chat.in.ChatMessageIn;
import com.mick.mchat.handlers.chat.out.ChatMessageOut;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.WsContextStore;
import com.mick.mchat.websocket.inbound.InMessageHandler;
import com.mick.mchat.websocket.inbound.InMessageType;
import com.mick.mchat.websocket.inbound.MChatMessageHandler;
import com.mick.mchat.websocket.outbound.OutMessageType;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatMessageHandler implements InMessageHandler {
    private final WsContextStore wsContextStore;

    @Inject
    public ChatMessageHandler(WsContextStore wsContextStore) {
        this.wsContextStore = wsContextStore;
    }

    @MChatMessageHandler(
            inType = InMessageType.CHAT_MESSAGE
    )
    public void handleChatMessage(ChatMessageIn inboundChatMessage, AuthenticationToken authenticationToken) {
        String message = inboundChatMessage.getMessage();

        wsContextStore.getAllWsContexts().forEach(wsContext -> wsContext.send(
                OutMessageWrapper.of(OutMessageType.CHAT_MESSAGE)
                        .body(
                                new ChatMessageOut()
                                        .setMessage(message))
                )
        );
    }
}
