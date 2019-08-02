package com.mick.mchat.handlers.conversation;

import com.mick.mchat.error.NotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
public class ConversationStore {
    private List<Conversation> conversations;

    @Inject
    public ConversationStore() {
        this.conversations = new ArrayList<>();

        //for now lets just make 1 conversation
        createConversation(List.of(UUID.randomUUID(), UUID.randomUUID()));
    }

    public Conversation getConversation(UUID uuid) throws NotFoundException {
        return conversations.stream()
                .filter(conversation -> conversation.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("No conversation for UUID:" + uuid));
    }

    //for now just have 1
    public void addUserToConversation(UUID userUuid){
        conversations.get(0).getParticipants().add(userUuid);
    }

    public List<Conversation> getConversationsForUser(UUID userUuid){
        return conversations;
    }

    public Conversation createConversation(final List<UUID> participants){
        Conversation conversation = new Conversation()
                .setUuid(UUID.randomUUID())
                .setParticipants(participants);

        conversations.add(conversation);

        return conversation;
    }

    public Conversation updateConversation(final Conversation updatedConversation) throws NotFoundException {
        Optional<Conversation> optionalConversation = conversations.stream()
                .filter(conversation -> conversation.getUuid().equals(updatedConversation.getUuid()))
                .findFirst();

        Conversation conversation = optionalConversation.orElseThrow(NotFoundException::new);

        conversations.remove(conversation);
        conversations.add(updatedConversation);

        return conversation;
    }
}
