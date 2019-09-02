package com.mick.mchat.websocket.outbound;

public enum OutMessageType {
    NONE,
    LOGIN_RESPONSE,
    CONVERSATIONS_GET_ALL_RESPONSE,
    CHAT_MESSAGE,
    CURRENT_USER,
    ERROR,
    USERS_ALL,
    USER_TYPING;
}
