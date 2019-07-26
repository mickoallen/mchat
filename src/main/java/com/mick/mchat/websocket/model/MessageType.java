package com.mick.mchat.websocket.model;

public enum MessageType {
    CHAT_MESSAGE(MessageType.CHAT_MESSAGE_VALUE),
    USER_LOGIN(MessageType.USER_LOGIN_VALUE),
    USER_CONNECTED(MessageType.USER_CONNECTED_VALUE);

    public static final String CHAT_MESSAGE_VALUE = "CHAT_MESSAGE";
    public static final String USER_LOGIN_VALUE = "USER_LOGIN";
    public static final String USER_CONNECTED_VALUE = "USER_CONNECTED";

    private String value;

    MessageType(String chatMessageValue) {
        this.value = chatMessageValue;
    }

    public String getValue() {
        return value;
    }
}
