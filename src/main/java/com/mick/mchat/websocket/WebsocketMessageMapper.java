package com.mick.mchat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mick.mchat.websocket.model.WebsocketMessage;

import java.io.IOException;

public class WebsocketMessageMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static WebsocketMessage deserialize(String message) throws IOException {
        return objectMapper.readValue(message, WebsocketMessage.class);
    }

    public static String serialize(Object maybeAWebsocketMessageMaybeNot) throws IOException {
        return objectMapper.writeValueAsString(maybeAWebsocketMessageMaybeNot);
    }
}
