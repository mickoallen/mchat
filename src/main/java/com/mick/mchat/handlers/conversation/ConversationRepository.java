package com.mick.mchat.handlers.conversation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class ConversationRepository {
    public final ConversationStore conversationStore;

    @Inject
    public ConversationRepository(ConversationStore conversationStore) {
        this.conversationStore = conversationStore;
    }

    public List<Conversation> getConversationsForUser(UUID user){
        return conversationStore.getConversationsForUser(user);
    }

    public void addUserToConversation(UUID userUuid){
        conversationStore.addUserToConversation(userUuid);
    }
}
