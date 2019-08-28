/*
 * This file is generated by jOOQ.
 */
package com.mick.mchat.jooq.model.tables;


import com.mick.mchat.jooq.model.Indexes;
import com.mick.mchat.jooq.model.Keys;
import com.mick.mchat.jooq.model.Public;
import com.mick.mchat.jooq.model.tables.records.UserConversationRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class UserConversation extends TableImpl<UserConversationRecord> {

    private static final long serialVersionUID = -635243096;

    /**
     * The reference instance of <code>public.user_conversation</code>
     */
    public static final UserConversation USER_CONVERSATION = new UserConversation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserConversationRecord> getRecordType() {
        return UserConversationRecord.class;
    }

    /**
     * The column <code>public.user_conversation.uuid</code>.
     */
    public final TableField<UserConversationRecord, UUID> UUID = createField("uuid", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_conversation.conversation_uuid</code>.
     */
    public final TableField<UserConversationRecord, UUID> CONVERSATION_UUID = createField("conversation_uuid", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_conversation.user_uuid</code>.
     */
    public final TableField<UserConversationRecord, UUID> USER_UUID = createField("user_uuid", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_conversation.date_created</code>.
     */
    public final TableField<UserConversationRecord, Timestamp> DATE_CREATED = createField("date_created", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>public.user_conversation</code> table reference
     */
    public UserConversation() {
        this(DSL.name("user_conversation"), null);
    }

    /**
     * Create an aliased <code>public.user_conversation</code> table reference
     */
    public UserConversation(String alias) {
        this(DSL.name(alias), USER_CONVERSATION);
    }

    /**
     * Create an aliased <code>public.user_conversation</code> table reference
     */
    public UserConversation(Name alias) {
        this(alias, USER_CONVERSATION);
    }

    private UserConversation(Name alias, Table<UserConversationRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserConversation(Name alias, Table<UserConversationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> UserConversation(Table<O> child, ForeignKey<O, UserConversationRecord> key) {
        super(child, key, USER_CONVERSATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_CONVERSATION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserConversationRecord> getPrimaryKey() {
        return Keys.USER_CONVERSATION_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserConversationRecord>> getKeys() {
        return Arrays.<UniqueKey<UserConversationRecord>>asList(Keys.USER_CONVERSATION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserConversation as(String alias) {
        return new UserConversation(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserConversation as(Name alias) {
        return new UserConversation(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserConversation rename(String name) {
        return new UserConversation(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserConversation rename(Name name) {
        return new UserConversation(name, null);
    }
}
