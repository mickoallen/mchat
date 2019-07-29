package com.mick.mchat.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mick.mchat.error.JsonMappingException;
import com.mick.mchat.websocket.model.WebsocketMessage;

import java.io.IOException;

public class WebsocketMessageMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static WebsocketMessage deserialize(String message) {
        try {
            return objectMapper.readValue(message, WebsocketMessage.class);
        } catch (IOException e) {
            throw new JsonMappingException(e);
        }
    }

    public static String serialize(Object maybeAWebsocketMessageMaybeNot) {
        try {
            return objectMapper.writeValueAsString(maybeAWebsocketMessageMaybeNot);
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(e);
        }
    }
}
