package com.mick.mchat.chat;

import com.mick.mchat.websocket.model.MessageBody;
import com.mick.mchat.websocket.model.MessageType;

import java.util.UUID;


public class ChatMessageBody extends MessageBody {
    private UUID conversationUuid;
    private ChatMessageModel messageBody;

    public ChatMessageBody() {
        super(MessageType.CHAT_MESSAGE);
    }

    public UUID getConversationUuid() {
        return conversationUuid;
    }

    public ChatMessageBody setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
        return this;
    }

    public ChatMessageModel getMessageBody() {
        return messageBody;
    }

    public ChatMessageBody setMessageBody(ChatMessageModel messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    @Override
    public String toString() {
        return "ChatMessageBody{" +
                "conversationUuid=" + conversationUuid +
                ", messageBody=" + messageBody +
                '}';
    }
}
