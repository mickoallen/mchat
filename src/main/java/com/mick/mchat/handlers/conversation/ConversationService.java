package com.mick.mchat.handlers.conversation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class ConversationService {
    private final ConversationRepository conversationRepository;

    @Inject
    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation getConversation(UUID conversationUuid, UUID userUuid) {
        return null;
    }

    public List<Conversation> getAllConversationsForUser(UUID userUuid) {
        return conversationRepository.getConversationsForUser(userUuid);
    }
}
