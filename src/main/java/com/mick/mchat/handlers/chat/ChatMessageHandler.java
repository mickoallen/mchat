package com.mick.mchat.handlers.chat;

import com.mick.mchat.handlers.chat.in.ChatMessageIn;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.inbound.InMessageHandler;
import com.mick.mchat.websocket.inbound.InMessageType;
import com.mick.mchat.websocket.inbound.MChatMessageHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Handles chat messages.
 */
@Singleton
public class ChatMessageHandler implements InMessageHandler {
    private final ChatMessageService chatMessageService;

    @Inject
    public ChatMessageHandler(
            final ChatMessageService chatMessageService
    ) {
        this.chatMessageService = chatMessageService;
    }

    /**
     * Handle an inbound chat message.
     * @param inboundChatMessage
     * @param authenticationToken
     */
    @MChatMessageHandler(
            inType = InMessageType.CHAT_MESSAGE
    )
    public void handleChatMessage(ChatMessageIn inboundChatMessage, AuthenticationToken authenticationToken) {
        chatMessageService.handleInboundMessage(inboundChatMessage, authenticationToken);
    }
}
