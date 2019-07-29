package com.mick.mchat.websocket.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mick.mchat.chat.ChatMessageBody;
import com.mick.mchat.conversation.ConversationsGetMessage;
import com.mick.mchat.user.login.UserLoginMessageBody;
import com.mick.mchat.user.security.AddCookieMessage;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "messageType",
        defaultImpl = DefaultMessageBody.class,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChatMessageBody.class, name = MessageType.CHAT_MESSAGE_VALUE),
        @JsonSubTypes.Type(value = UserLoginMessageBody.class, name = MessageType.USER_LOGIN_VALUE),
        @JsonSubTypes.Type(value = ConversationsGetMessage.class, name = MessageType.CONVERSATIONS_GET_VALUE),
        @JsonSubTypes.Type(value = AddCookieMessage.class, name = MessageType.ADD_COOKIE_VALUE)
})
public abstract class MessageBody {
    private final MessageType messageType;

    protected MessageBody(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
