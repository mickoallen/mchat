package com.mick.mchat.handlers.conversation;

import com.mick.mchat.handlers.chat.ChatMessageMapper;
import com.mick.mchat.handlers.conversation.out.ConversationOut;
import com.mick.mchat.handlers.conversation.out.ConversationsOut;
import com.mick.mchat.jooq.model.tables.pojos.Conversation;
import com.mick.mchat.jooq.model.tables.pojos.Message;
import com.mick.mchat.jooq.model.tables.pojos.UserConversation;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * For mapping conversations
 */
public class ConversationMapper {
    public static ConversationsOut toConversationsOut(List<Conversation> conversations, List<UserConversation> userConversations, Map<UUID, List<Message>> messagesForConversations) {
        return new ConversationsOut()
                .setConversations(
                        conversations
                                .stream()
                                .map(conversation ->
                                        toConversationOut(
                                                conversation,
                                                getUserConversations(conversation, userConversations),
                                                messagesForConversations.get(conversation.getUuid())
                                        )
                                )
                                .collect(Collectors.toList())
                );
    }

    public static ConversationOut toConversationOut(Conversation conversation, List<UserConversation> userConversations, List<Message> messages) {
        return new ConversationOut()
                .setUuid(conversation.getUuid())
                .setType(conversation.getType())
                .setName(conversation.getName())
                .setParticipants(
                        userConversations
                                .stream()
                                .map(UserConversation::getUserUuid)
                                .collect(Collectors.toSet())
                )
                .setMessages(
                        messages
                                .stream()
                                .map(ChatMessageMapper::toChatMessageOut)
                                .collect(Collectors.toSet())
                );
    }

    private static List<UserConversation> getUserConversations(Conversation conversation, List<UserConversation> userConversations){
        return userConversations
                .stream()
                .filter(
                        userConversation ->
                                conversation.getUuid().equals(userConversation.getConversationUuid())
                ).collect(Collectors.toList());
    }
}
