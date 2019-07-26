package com.mick.mchat.conversation.ws;

import com.mick.mchat.websocket.WebsocketMessage;
import com.mick.mchat.websocket.WebsocketMessageType;
import com.mick.mchat.conversation.ChatMessageApiModel;

import java.util.UUID;


public class WebsocketChatMessage extends WebsocketMessage {
    private UUID conversationUuid;
    private ChatMessageApiModel messageBody;

    public WebsocketChatMessage() {
        setWebsocketMessageType(WebsocketMessageType.CHAT_MESSAGE);
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public WebsocketChatMessage setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }

    public ChatMessageApiModel getMessageBody() {
        return messageBody;
    }

    public WebsocketChatMessage setMessageBody(ChatMessageApiModel messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    @Override
    public String toString() {
        return "WebsocketChatMessage{" +
                "conversationUuid=" + conversationUuid +
                ", messageBody=" + messageBody +
                '}';
    }
}
