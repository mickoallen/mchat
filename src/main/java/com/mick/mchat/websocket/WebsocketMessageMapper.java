package com.mick.mchat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class WebsocketMessageMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static WebsocketMessage deserialize(String message) throws IOException {
        return objectMapper.readValue(message, WebsocketMessage.class);
    }

    public static String serialize(WebsocketMessage websocketMessage) throws IOException {
        return objectMapper.writeValueAsString(websocketMessage);
    }
}
