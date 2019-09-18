package com.mick.mchat.handlers.conversation;

import com.mick.mchat.handlers.chat.ChatMessageService;
import com.mick.mchat.handlers.conversation.in.CreateConversationIn;
import com.mick.mchat.jooq.model.tables.pojos.Conversation;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.WsContextStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConversationMessageHandlerTest {
    private ConversationMessageHandler conversationMessageHandler;
    private ConversationService conversationService;
    private ChatMessageService chatMessageService;
    private WsContextStore wsContextStore;

    @BeforeEach
    public void init() {
        conversationService = mock(ConversationService.class);
        chatMessageService = mock(ChatMessageService.class);
        wsContextStore = mock(WsContextStore.class);

        conversationMessageHandler = new ConversationMessageHandler(conversationService, chatMessageService, wsContextStore);
    }

    @Test
    public void testConversationCreation() {
        List<UUID> users = new ArrayList<>(List.of(UUID.randomUUID()));//make list mutable, probably should do this differently
        AuthenticationToken authenticationToken = new AuthenticationToken()
                .setUserUuid(UUID.randomUUID());

        CreateConversationIn createConversationIn = new CreateConversationIn()
                .setName("A cool new conversation")
                .setUsers(users);

        conversationMessageHandler.createConversation(createConversationIn, authenticationToken);

        ArgumentCaptor<Conversation> conversationArgumentCaptor = ArgumentCaptor.forClass(Conversation.class);
        ArgumentCaptor<List> usersArgumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(conversationService, times(1)).createConversationForUsers(conversationArgumentCaptor.capture(), usersArgumentCaptor.capture());

        Conversation createdConversation = conversationArgumentCaptor.getValue();
        @SuppressWarnings("unchecked") List<UUID> usersInConversation = usersArgumentCaptor.getValue();

        assertEquals(createConversationIn.getName(), createdConversation.getName());
        assertEquals(usersInConversation, List.of(createConversationIn.getUsers().get(0), authenticationToken.getUserUuid()));
    }
}
