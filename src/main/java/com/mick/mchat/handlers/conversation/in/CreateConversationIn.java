package com.mick.mchat.handlers.conversation.in;

import com.mick.mchat.websocket.inbound.InMessage;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Create a conversation
 */
public class CreateConversationIn implements InMessage {
    private List<UUID> users;
    private String name;

    public List<UUID> getUsers() {
        return users;
    }

    public CreateConversationIn setUsers(List<UUID> users) {
        this.users = users;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateConversationIn setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "CreateConversationIn{" +
                "users=" + users +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateConversationIn that = (CreateConversationIn) o;
        return Objects.equals(users, that.users) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, name);
    }
}
