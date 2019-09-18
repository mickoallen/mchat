package com.mick.mchat.handlers.chat;

import com.mick.mchat.handlers.chat.out.ChatMessageOut;
import com.mick.mchat.jooq.model.tables.pojos.Message;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatMessageMapperTest {
    @Test
    public void testChatMessageMapping() {
        Message message = new Message();
        message.setBody("Some cool chat message body");
        message.setDateCreated(Timestamp.from(Instant.ofEpochMilli(1234456L)));
        message.setType("TEXT");
        message.setUserUuid(UUID.randomUUID());
        message.setConversationUuid(UUID.randomUUID());
        message.setUuid(UUID.randomUUID());

        ChatMessageOut chatMessageOut = ChatMessageMapper.toChatMessageOut(message);

        assertEquals(message.getType(), chatMessageOut.getType().name());
        assertEquals(message.getBody(), chatMessageOut.getMessage());
        assertEquals(1234456L, chatMessageOut.getDateCreated());
        assertEquals(message.getUserUuid(), chatMessageOut.getUserUuid());
        assertEquals(message.getConversationUuid(), chatMessageOut.getConversationUuid());
    }
}
