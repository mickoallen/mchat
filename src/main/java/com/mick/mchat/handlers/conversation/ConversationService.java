package com.mick.mchat.handlers.conversation;

import com.mick.mchat.jooq.model.enums.ConversationType;
import com.mick.mchat.jooq.model.tables.daos.ConversationDao;
import com.mick.mchat.jooq.model.tables.daos.UserConversationDao;
import com.mick.mchat.jooq.model.tables.pojos.Conversation;
import com.mick.mchat.jooq.model.tables.pojos.UserConversation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class ConversationService {
    private final ConversationDao conversationDao;
    private final UserConversationDao userConversationDao;

    @Inject
    public ConversationService(
            final ConversationDao conversationDao,
            final UserConversationDao userConversationDao
    ) {
        this.conversationDao = conversationDao;
        this.userConversationDao = userConversationDao;
    }

    public void createConversationForUsers(List<UUID> users) {
        Timestamp now = new Timestamp(Instant.now().toEpochMilli());

        Conversation conversation = new Conversation();
        conversation.setDateCreated(now);
        conversation.setName("A random name " + Math.random());

        if (users.size() <= 2) {
            conversation.setType(ConversationType.PRIVATE);
        } else {
            conversation.setType(ConversationType.GROUP);
        }

        conversation.setUuid(UUID.randomUUID());

        conversationDao.insert(conversation);
        userConversationDao.insert(
                users
                        .stream()
                        .map(uuid -> {
                            UserConversation userConversation = new UserConversation();
                            userConversation.setUuid(UUID.randomUUID());
                            userConversation.setUserUuid(uuid);
                            userConversation.setConversationUuid(conversation.getUuid());
                            userConversation.setDateCreated(now);
                            return userConversation;
                        }).collect(Collectors.toList())
        );
    }

    public List<Conversation> getAllConversationsForUser(UUID userUuid) {
        List<UserConversation> userConversations = userConversationDao.fetchByUserUuid(userUuid);

        if (userConversations.isEmpty()) {
            return List.of();
        }

        return conversationDao.fetchByUuid(
                userConversations
                        .stream()
                        .map(UserConversation::getConversationUuid)
                        .collect(Collectors.toList())
                        .toArray(UUID[]::new)
        );
    }

    public List<UserConversation> getUserConversations(List<UUID> conversationUuids) {
        return userConversationDao.fetchByConversationUuid(conversationUuids.toArray(UUID[]::new));
    }

    public Conversation getConversation(UUID conversationUuid) {
        return conversationDao.fetchOneByUuid(conversationUuid);
    }
}
