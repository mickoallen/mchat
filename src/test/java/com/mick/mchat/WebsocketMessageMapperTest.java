package com.mick.mchat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mick.mchat.conversation.ChatMessageApiModel;
import com.mick.mchat.conversation.ws.WebsocketChatMessage;
import com.mick.mchat.websocket.WebsocketMessage;
import com.mick.mchat.websocket.WebsocketMessageMapper;
import com.mick.mchat.websocket.WebsocketMessageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebsocketMessageMapperTest {
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testMessageDeserialization() throws Exception {

    }
}
