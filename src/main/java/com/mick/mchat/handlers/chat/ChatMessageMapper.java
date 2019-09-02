package com.mick.mchat.handlers.chat;

import com.mick.mchat.handlers.chat.out.ChatMessageOut;
import com.mick.mchat.handlers.chat.out.UserTypingOut;
import com.mick.mchat.jooq.model.tables.pojos.Message;

import java.util.UUID;

public class ChatMessageMapper {
    public static ChatMessageOut toChatMessageOut(Message message) {
        return new ChatMessageOut()
                .setMessage(message.getBody())
                .setDateCreated(message.getDateCreated().getTime())
                .setType(message.getType())
                .setUserUuid(message.getUserUuid())
                .setConversationUuid(message.getConversationUuid());
    }

    public static UserTypingOut toTypingMessageOut(UUID userUuid, UUID conversationUuid) {
        return new UserTypingOut()
                .setUserUuid(userUuid)
                .setConversationUuid(conversationUuid)
                ;
    }
}
