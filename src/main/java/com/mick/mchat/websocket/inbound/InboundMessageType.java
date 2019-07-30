package com.mick.mchat.websocket.inbound;

public enum InboundMessageType {
    NONE("NONE"),
    CHAT_MESSAGE(InboundMessageType.CHAT_MESSAGE_VALUE),
    CONVERSATION_GET(InboundMessageType.CONVERSATION_GET_VALUE),
    USER_LOGIN(InboundMessageType.USER_LOGIN_VALUE, false),
    USER_CONNECTED(InboundMessageType.USER_CONNECTED_VALUE);


    public static final String CHAT_MESSAGE_VALUE = "CHAT_MESSAGE";
    public static final String CONVERSATION_GET_VALUE = "CONVERSATION_GET";
    public static final String USER_LOGIN_VALUE = "USER_LOGIN";
    public static final String USER_CONNECTED_VALUE = "USER_CONNECTED";

    private String value;
    private boolean authenticationRequired = true;

    InboundMessageType(String chatMessageValue) {
        this.value = chatMessageValue;
    }

    InboundMessageType(String chatMessageValue, boolean authenticationRequired) {
        this.value = chatMessageValue;
        this.authenticationRequired = authenticationRequired;
    }

    public String getValue() {
        return value;
    }

    public boolean isAuthenticationRequired() {
        return authenticationRequired;
    }
}
