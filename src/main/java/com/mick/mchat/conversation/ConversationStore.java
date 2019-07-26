package com.mick.mchat.conversation;

import com.mick.mchat.NotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class ConversationStore {
    private List<Conversation> conversations;

    @Inject
    public ConversationStore() {
        this.conversations = new ArrayList<>();

        //for now lets just make 1 conversation
        createConversation(new ArrayList<>());
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

    public Collection<Conversation> getConversationsForUser(UUID userUuid){
        return conversations.stream()
                .filter(conversation -> conversation.getParticipants().stream()
                            .anyMatch(participantUuid -> participantUuid.equals(userUuid))
                ).collect(Collectors.toList());
    }

    public Conversation createConversation(final List<UUID> participants){
        Conversation conversation = new Conversation()
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
