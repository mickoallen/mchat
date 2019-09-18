package com.mick.mchat.handlers.chat;

import com.mick.mchat.jooq.model.tables.daos.MessageDao;
import com.mick.mchat.jooq.model.tables.pojos.Message;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;

/**
 * Abstracting the DB layer from the business logic.
 *
 * Since jooq has a combination of easy to mock methods, very not easy to mock
 * interactions with the dsl context directly (2 great examples below) i've decoupled
 * all the business logic to the service so it can be easily unit tests.
 * I'll run the things below in an integration test hitting a real db.
 */
@Singleton
public class ChatMessageRepository {
    private final MessageDao messageDao;

    @Inject
    public ChatMessageRepository(final MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    /**
     * Get the list of messages for the specified conversation.
     *
     * @param conversationUuid
     * @param messageFetchLimit
     * @return
     */
    public List<Message> getMessagesForConversation(final UUID conversationUuid, final int messageFetchLimit){
        return messageDao
                .configuration()
                .dsl()
                .selectFrom(messageDao.getTable())
                .where(
                        field(com.mick.mchat.jooq.model.tables.Message.MESSAGE.CONVERSATION_UUID).eq(conversationUuid)
                )
                .orderBy(field(com.mick.mchat.jooq.model.tables.Message.MESSAGE.DATE_CREATED).desc())
                .limit(messageFetchLimit)
                .fetchInto(Message.class);
    }

    /**
     * Create a new chat message.
     *
     * @param message
     * @return
     */
    public Message create(Message message) {
        messageDao.insert(message);
        return message;
    }
}
