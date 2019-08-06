package com.mick.mchat.handlers.chat;

import com.mick.mchat.jooq.model.tables.daos.MessageDao;
import com.mick.mchat.jooq.model.tables.pojos.Message;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;

@Singleton
public class ChatMessageService {
    private final MessageDao messageDao;
    private final int messageFetchLimit = 100;

    @Inject
    public ChatMessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public Map<UUID, List<Message>> getMessagesForConversations(List<UUID> conversationUuids, long messageOffset) {
        return conversationUuids
                .stream()
                .map(conversationUuid ->
                        Map.entry(
                                conversationUuid,
                                messageDao
                                        .configuration()
                                        .dsl()
                                        .selectFrom(messageDao.getTable())
                                        .where(
                                                field(com.mick.mchat.jooq.model.tables.Message.MESSAGE.CONVERSATION_UUID).eq(conversationUuid)
                                        )
                                        .orderBy(field(com.mick.mchat.jooq.model.tables.Message.MESSAGE.DATE_CREATED).desc())
                                        .limit(messageFetchLimit)
                                        .fetchInto(Message.class)
                        )
                )
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        )
                );
    }
}
