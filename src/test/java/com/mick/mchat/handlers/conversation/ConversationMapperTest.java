package com.mick.mchat.handlers.conversation;

import com.mick.mchat.handlers.chat.out.ChatMessageType;
import com.mick.mchat.handlers.conversation.out.ConversationOut;
import com.mick.mchat.handlers.conversation.out.ConversationsOut;
import com.mick.mchat.jooq.model.tables.pojos.Conversation;
import com.mick.mchat.jooq.model.tables.pojos.Message;
import com.mick.mchat.jooq.model.tables.pojos.UserConversation;
import com.mick.mchat.util.TimeProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversationMapperTest {
    private static TimeProvider timeProvider;

    @BeforeAll
    public static void init() {
        timeProvider = new TimeProvider();
    }

    @Test
    public void testMapping() {
        Timestamp nowAsTimestamp = timeProvider.getNowAsTimestamp();

        Conversation conversation = new Conversation();
        conversation.setDateCreated(nowAsTimestamp);
        conversation.setUuid(UUID.randomUUID());
        conversation.setName("A cool conversation");

        UserConversation userConversation1 = new UserConversation();
        userConversation1.setUserUuid(UUID.randomUUID());
        userConversation1.setDateCreated(nowAsTimestamp);
        userConversation1.setConversationUuid(conversation.getUuid());
        userConversation1.setUuid(UUID.randomUUID());

        UserConversation userConversation2 = new UserConversation();
        userConversation2.setUserUuid(UUID.randomUUID());
        userConversation2.setDateCreated(nowAsTimestamp);
        userConversation2.setConversationUuid(conversation.getUuid());
        userConversation2.setUuid(UUID.randomUUID());

        Message message1 = new Message();
        message1.setBody("A cool message 1");
        message1.setUserUuid(userConversation1.getUserUuid());
        message1.setConversationUuid(conversation.getUuid());
        message1.setDateCreated(nowAsTimestamp);
        message1.setType(ChatMessageType.TEXT.name());

        Message message2 = new Message();
        message2.setBody("A cool message 2");
        message2.setUserUuid(userConversation1.getUserUuid());
        message2.setConversationUuid(conversation.getUuid());
        message2.setDateCreated(nowAsTimestamp);
        message2.setType(ChatMessageType.TEXT.name());

        Message message3 = new Message();
        message3.setBody("A cool message 3");
        message3.setUserUuid(userConversation2.getUserUuid());
        message3.setConversationUuid(conversation.getUuid());
        message3.setDateCreated(nowAsTimestamp);
        message3.setType(ChatMessageType.TEXT.name());

        ConversationsOut conversationsOut = ConversationMapper.toConversationsOut(
                List.of(conversation),
                List.of(userConversation1, userConversation2),
                Map.of(conversation.getUuid(), List.of(message1, message2, message3))
        );

        assertEquals(1, conversationsOut.getConversations().size());
        ConversationOut conversationOut = conversationsOut.getConversations().get(0);

        assertEquals(conversation.getUuid(), conversationOut.getUuid());
        assertEquals(conversation.getName(), conversationOut.getName());
        assertEquals(2, conversationOut.getParticipants().size());
        assertEquals(3, conversationOut.getMessages().size());
    }
}
