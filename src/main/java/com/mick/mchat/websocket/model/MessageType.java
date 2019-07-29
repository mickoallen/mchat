package com.mick.mchat.websocket.model;

public enum MessageType {
    NONE("NONE"),
    CHAT_MESSAGE(MessageType.CHAT_MESSAGE_VALUE),

    CONVERSATIONS_GET(MessageType.CONVERSATIONS_GET_VALUE),

    USER_LOGIN(MessageType.USER_LOGIN_VALUE),
    USER_CONNECTED(MessageType.USER_CONNECTED_VALUE),

    ADD_COOKIE(MessageType.ADD_COOKIE_VALUE);

    public static final String CHAT_MESSAGE_VALUE = "CHAT_MESSAGE";

    public static final String CONVERSATIONS_GET_VALUE = "CONVERSATIONS_GET";

    public static final String USER_LOGIN_VALUE = "USER_LOGIN";
    public static final String USER_CONNECTED_VALUE = "USER_CONNECTED";
    public static final String ADD_COOKIE_VALUE = "ADD_COOKIE";

    private String value;

    MessageType(String chatMessageValue) {
        this.value = chatMessageValue;
    }

    public String getValue() {
        return value;
    }
}
