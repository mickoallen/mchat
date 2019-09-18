package com.mick.mchat.handlers.chat;

import com.mick.mchat.handlers.chat.in.ChatMessageIn;
import com.mick.mchat.handlers.conversation.ConversationService;
import com.mick.mchat.jooq.model.tables.pojos.Message;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.util.TimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatMessageServiceTest {
    private ChatMessageService chatMessageService;
    private ConversationService conversationService;
    private ChatMessageRepository chatMessageRepository;
    private TimeProvider timeProvider;

    @BeforeEach
    public void init() {
        conversationService = mock(ConversationService.class);
        chatMessageRepository = mock(ChatMessageRepository.class);
        timeProvider = mock(TimeProvider.class);
        chatMessageService = new ChatMessageService(chatMessageRepository, conversationService, timeProvider);
    }

    @Test
    public void testGettingMessagesForNoConversations() {
        Map<UUID, List<Message>> messagesForConversations = chatMessageService.getMessagesForConversations(List.of());

        assertTrue(messagesForConversations.isEmpty());
    }

    @Test
    public void testGettingMessagesForSingleConversation() {
        UUID conversationUuid = UUID.randomUUID();

        when(chatMessageRepository.getMessagesForConversation(conversationUuid, 40)).thenReturn(createTestMessages(conversationUuid, 10));

        Map<UUID, List<Message>> messagesForConversations = chatMessageService.getMessagesForConversations(List.of(conversationUuid));

        assertEquals(1, messagesForConversations.size());
        assertEquals(10, messagesForConversations.get(conversationUuid).size());
    }

    @Test
    public void testGettingMessagesForMultipleConversations() {
        UUID conversation1Uuid = UUID.randomUUID();
        UUID conversation2Uuid = UUID.randomUUID();

        when(chatMessageRepository.getMessagesForConversation(conversation1Uuid, 40))
                .thenReturn(createTestMessages(conversation1Uuid, 10));

        when(chatMessageRepository.getMessagesForConversation(conversation2Uuid, 40))
                .thenReturn(createTestMessages(conversation2Uuid, 15));

        Map<UUID, List<Message>> messagesForConversations = chatMessageService.getMessagesForConversations(
                List.of(conversation1Uuid, conversation2Uuid));

        assertEquals(2, messagesForConversations.size());
        assertEquals(10, messagesForConversations.get(conversation1Uuid).size());
        assertEquals(15, messagesForConversations.get(conversation2Uuid).size());
    }

    @Test
    public void testCreatingAChatMessage(){
        ChatMessageIn chatMessageIn = new ChatMessageIn()
                .setMessage("A cool message")
                .setConversationUuid(UUID.randomUUID());
        AuthenticationToken authenticationToken = new AuthenticationToken()
                .setUserUuid(UUID.randomUUID());

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

        Timestamp currentTime = Timestamp.from(Instant.now());
        when(timeProvider.getNowAsTimestamp()).thenReturn(currentTime);
        when(chatMessageRepository.create(messageArgumentCaptor.capture())).thenReturn(new Message());

        chatMessageService.handleInboundMessage(chatMessageIn, authenticationToken);

        Message createdMessage = messageArgumentCaptor.getValue();

        assertEquals(chatMessageIn.getMessage(), createdMessage.getBody());
        assertEquals(authenticationToken.getUserUuid(), createdMessage.getUserUuid());
        assertEquals(chatMessageIn.getConversationUuid(), createdMessage.getConversationUuid());
        assertEquals(currentTime, createdMessage.getDateCreated());

    }

    private List<Message> createTestMessages(UUID conversationUuid, int numberOfMessages) {
        return IntStream.range(0, numberOfMessages)
                .boxed()
                .map(i -> {
                    Message message = new Message();
                    message.setUuid(UUID.randomUUID());
                    message.setConversationUuid(conversationUuid);
                    message.setUserUuid(UUID.randomUUID());
                    message.setBody("Message number " + i);
                    return message;
                }).collect(Collectors.toList());
    }
}
