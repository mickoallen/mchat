package com.mick.mchat.websocket.inbound;

import com.mick.mchat.handlers.chat.in.ChatMessageIn;
import com.mick.mchat.handlers.conversation.in.AddUsersToConversationIn;
import com.mick.mchat.handlers.conversation.in.ConversationGetIn;
import com.mick.mchat.handlers.user.in.CurrentUserGetIn;
import com.mick.mchat.handlers.user.in.UserConnectedIn;
import com.mick.mchat.handlers.user.in.UserLoginIn;

public enum InMessageType {
    NONE,
    CHAT_MESSAGE(ChatMessageIn.class),
    CONVERSATIONS_GET_ALL(ConversationGetIn.class),
    USER_LOGIN(UserLoginIn.class, false),
    USER_CONNECTED(UserConnectedIn.class),
    CURRENT_USER_GET(CurrentUserGetIn.class),
    ADD_USERS_TO_CONVERSATION(AddUsersToConversationIn.class);

    private boolean authenticationRequired = true;
    private Class<? extends InMessage> clazz;

    InMessageType(){}

    InMessageType(Class<? extends InMessage> clazz) {
        this.clazz = clazz;
    }

    InMessageType(Class<? extends InMessage> clazz, boolean authenticationRequired) {
        this.clazz = clazz;
        this.authenticationRequired = authenticationRequired;
    }

    public boolean isAuthenticationRequired() {
        return authenticationRequired;
    }

    public Class<? extends InMessage> getClazz(){
        return clazz;
    }
}
