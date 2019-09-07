package com.mick.mchat.handlers.user;

import com.mick.mchat.error.NotFoundException;
import com.mick.mchat.jooq.model.tables.daos.UserDao;
import com.mick.mchat.jooq.model.tables.pojos.User;
import com.mick.mchat.security.AuthenticationService;
import com.mick.mchat.security.PasswordService;
import com.mick.mchat.websocket.WsContextStore;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;

@Singleton
public class UserService {
    private final UserDao userDao;
    private final PasswordService passwordService;
    private final WsContextStore wsContextStore;

    @Inject
    public UserService(
            final AuthenticationService authenticationService,
            final UserDao userDao,
            final PasswordService passwordService,
            final WsContextStore wsContextStore) {
        this.userDao = userDao;
        this.passwordService = passwordService;
        this.wsContextStore = wsContextStore;
    }

    public User createUser(User user) {
        List<User> users = userDao.fetchByUsername(user.getUsername());
        if(!users.isEmpty()){
            throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " is already taken.");
        }

        user.setUuid(UUID.randomUUID());
        user.setPassword(passwordService.getPasswordHash(user.getPassword()));
        user.setDateCreated(Timestamp.from(Instant.now()));
        userDao.insert(user);
        return user;
    }

    public User getUserFromCredentials(String username, String password) throws NotFoundException {
        return userDao
                .configuration()
                .dsl()
                .selectFrom(userDao.getTable())
                .where(field(com.mick.mchat.jooq.model.tables.User.USER.USERNAME)
                        .eq(username))
                .and(field(com.mick.mchat.jooq.model.tables.User.USER.PASSWORD)
                        .eq(passwordService.getPasswordHash(password)))
                .fetchOptionalInto(User.class)
                .orElseThrow(() -> new NotFoundException("No user for that username/password"));
    }

    public User getUser(UUID uuid) {
        User user = userDao
                .fetchOneByUuid(uuid);

        if (user == null) {
            throw new NotFoundException("No user for that uuid");
        }

        return user;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User login(String username, String password) {
        return getUserFromCredentials(username, password);
    }

    public User updateUser(User user) {
        userDao.update(user);
        return user;
    }
}
