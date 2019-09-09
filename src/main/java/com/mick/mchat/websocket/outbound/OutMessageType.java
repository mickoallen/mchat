package com.mick.mchat.websocket.outbound;

public enum OutMessageType {
    NONE,
    LOGIN_RESPONSE,
    CONVERSATIONS_GET_ALL_RESPONSE,
    CHAT_MESSAGE,
    CURRENT_USER,
    ERROR,
    USERS_ALL,
    USER_TYPING,
    USER_CREATED,
    USER_LOGGED_IN,
    USER_UPDATED;
}
