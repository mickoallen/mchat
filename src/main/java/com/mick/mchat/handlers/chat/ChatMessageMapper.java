package com.mick.mchat.handlers.chat;

import com.mick.mchat.handlers.chat.out.ChatMessageOut;
import com.mick.mchat.handlers.chat.out.ChatMessageType;
import com.mick.mchat.jooq.model.tables.pojos.Message;

/**
 * Mapper for chat messages
 */
public class ChatMessageMapper {
    public static ChatMessageOut toChatMessageOut(Message message) {
        return new ChatMessageOut()
                .setMessage(message.getBody())
                .setDateCreated(message.getDateCreated().getTime())
                .setType(ChatMessageType.valueOf(message.getType()))
                .setUserUuid(message.getUserUuid())
                .setConversationUuid(message.getConversationUuid());
    }
}
