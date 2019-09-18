package com.mick.mchat.handlers.conversation;

import com.mick.mchat.jooq.model.tables.pojos.Conversation;
import com.mick.mchat.jooq.model.tables.pojos.UserConversation;
import com.mick.mchat.websocket.WsContextStore;
import com.mick.mchat.websocket.outbound.OutMessageWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final WsContextStore wsContextStore;

    @Inject
    public ConversationService(
            final ConversationRepository conversationRepository,
            final WsContextStore wsContextStore) {
        this.conversationRepository = conversationRepository;
        this.wsContextStore = wsContextStore;
    }

    public void createConversationForUsers(Conversation conversation, List<UUID> users) {
        if (users.size() <= 2) {
            conversation.setType("PRIVATE");
        } else {
            conversation.setType("GROUP");
        }

        conversation.setUuid(UUID.randomUUID());

        conversationRepository.createConversation(conversation, users);
    }

    public List<Conversation> getAllConversationsForUser(UUID userUuid) {
        return conversationRepository.getConversationsForUser(userUuid);
    }

    public List<UserConversation> getUserConversations(List<UUID> conversationUuids) {
        return conversationRepository.getUserConversations(conversationUuids);
    }

    public void sendMessageToUsersInConversation(UUID conversationUuid, OutMessageWrapper outMessageWrapper) {
        List<UserConversation> userConversations = getUserConversations(List.of(conversationUuid));
        wsContextStore.getWsContextForUsers(
                userConversations
                        .stream()
                        .map(UserConversation::getUserUuid)
                        .collect(Collectors.toSet())
        )
                .forEach(wsContext -> wsContext.send(outMessageWrapper));
    }
}
