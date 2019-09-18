package com.mick.mchat.handlers.conversation;

import com.mick.mchat.jooq.model.tables.daos.ConversationDao;
import com.mick.mchat.jooq.model.tables.daos.UserConversationDao;
import com.mick.mchat.jooq.model.tables.pojos.Conversation;
import com.mick.mchat.jooq.model.tables.pojos.UserConversation;
import com.mick.mchat.util.TimeProvider;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class ConversationRepository {
    private final ConversationDao conversationDao;
    private final UserConversationDao userConversationDao;
    private final TimeProvider timeProvider;

    @Inject
    public ConversationRepository(
            final ConversationDao conversationDao,
            final UserConversationDao userConversationDao,
            final TimeProvider timeProvider) {
        this.conversationDao = conversationDao;
        this.userConversationDao = userConversationDao;
        this.timeProvider = timeProvider;
    }

    public void createConversation(Conversation conversation, List<UUID> users) {
        Timestamp now = timeProvider.getNowAsTimestamp();

        conversation.setDateCreated(now);

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

    public List<Conversation> getConversationsForUser(UUID userUuid) {
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
}
