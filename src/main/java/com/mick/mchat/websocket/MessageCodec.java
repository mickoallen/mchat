package com.mick.mchat.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mick.mchat.error.JsonMappingException;
import com.mick.mchat.websocket.inbound.InboundMessage;

import java.io.IOException;

public class MessageCodec {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static InboundMessage deserialize(String message) {
        try {
            return objectMapper.readValue(message, InboundMessage.class);
        } catch (IOException e) {
            throw new JsonMappingException(e);
        }
    }

    public static String serialize(Object yourmom) {
        try {
            return objectMapper.writeValueAsString(yourmom);
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(e);
        }
    }
}
