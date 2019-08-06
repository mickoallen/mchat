/*
 * This file is generated by jOOQ.
 */
package com.mick.mchat.jooq.model.tables.daos;


import com.mick.mchat.jooq.model.tables.User;
import com.mick.mchat.jooq.model.tables.records.UserRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class UserDao extends DAOImpl<UserRecord, com.mick.mchat.jooq.model.tables.pojos.User, UUID> {

    /**
     * Create a new UserDao without any configuration
     */
    public UserDao() {
        super(User.USER, com.mick.mchat.jooq.model.tables.pojos.User.class);
    }

    /**
     * Create a new UserDao with an attached configuration
     */
    public UserDao(Configuration configuration) {
        super(User.USER, com.mick.mchat.jooq.model.tables.pojos.User.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UUID getId(com.mick.mchat.jooq.model.tables.pojos.User object) {
        return object.getUuid();
    }

    /**
     * Fetch records that have <code>uuid IN (values)</code>
     */
    public List<com.mick.mchat.jooq.model.tables.pojos.User> fetchByUuid(UUID... values) {
        return fetch(User.USER.UUID, values);
    }

    /**
     * Fetch a unique record that has <code>uuid = value</code>
     */
    public com.mick.mchat.jooq.model.tables.pojos.User fetchOneByUuid(UUID value) {
        return fetchOne(User.USER.UUID, value);
    }

    /**
     * Fetch records that have <code>username IN (values)</code>
     */
    public List<com.mick.mchat.jooq.model.tables.pojos.User> fetchByUsername(String... values) {
        return fetch(User.USER.USERNAME, values);
    }

    /**
     * Fetch records that have <code>password IN (values)</code>
     */
    public List<com.mick.mchat.jooq.model.tables.pojos.User> fetchByPassword(String... values) {
        return fetch(User.USER.PASSWORD, values);
    }

    /**
     * Fetch records that have <code>avatar_url IN (values)</code>
     */
    public List<com.mick.mchat.jooq.model.tables.pojos.User> fetchByAvatarUrl(String... values) {
        return fetch(User.USER.AVATAR_URL, values);
    }

    /**
     * Fetch records that have <code>date_created IN (values)</code>
     */
    public List<com.mick.mchat.jooq.model.tables.pojos.User> fetchByDateCreated(Timestamp... values) {
        return fetch(User.USER.DATE_CREATED, values);
    }
}
