/*
 * This file is generated by jOOQ.
 */
package com.mick.mchat.jooq.model.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserConversation implements Serializable {

    private static final long serialVersionUID = 759995972;

    private UUID      uuid;
    private UUID      conversationUuid;
    private UUID      userUuid;
    private Timestamp dateCreated;

    public UserConversation() {}

    public UserConversation(UserConversation value) {
        this.uuid = value.uuid;
        this.conversationUuid = value.conversationUuid;
        this.userUuid = value.userUuid;
        this.dateCreated = value.dateCreated;
    }

    public UserConversation(
        UUID      uuid,
        UUID      conversationUuid,
        UUID      userUuid,
        Timestamp dateCreated
    ) {
        this.uuid = uuid;
        this.conversationUuid = conversationUuid;
        this.userUuid = userUuid;
        this.dateCreated = dateCreated;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getConversationUuid() {
        return this.conversationUuid;
    }

    public void setConversationUuid(UUID conversationUuid) {
        this.conversationUuid = conversationUuid;
    }

    public UUID getUserUuid() {
        return this.userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public Timestamp getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserConversation other = (UserConversation) obj;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        }
        else if (!uuid.equals(other.uuid))
            return false;
        if (conversationUuid == null) {
            if (other.conversationUuid != null)
                return false;
        }
        else if (!conversationUuid.equals(other.conversationUuid))
            return false;
        if (userUuid == null) {
            if (other.userUuid != null)
                return false;
        }
        else if (!userUuid.equals(other.userUuid))
            return false;
        if (dateCreated == null) {
            if (other.dateCreated != null)
                return false;
        }
        else if (!dateCreated.equals(other.dateCreated))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.uuid == null) ? 0 : this.uuid.hashCode());
        result = prime * result + ((this.conversationUuid == null) ? 0 : this.conversationUuid.hashCode());
        result = prime * result + ((this.userUuid == null) ? 0 : this.userUuid.hashCode());
        result = prime * result + ((this.dateCreated == null) ? 0 : this.dateCreated.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserConversation (");

        sb.append(uuid);
        sb.append(", ").append(conversationUuid);
        sb.append(", ").append(userUuid);
        sb.append(", ").append(dateCreated);

        sb.append(")");
        return sb.toString();
    }
}