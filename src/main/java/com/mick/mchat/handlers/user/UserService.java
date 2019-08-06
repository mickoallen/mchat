package com.mick.mchat.handlers.user;

import com.mick.mchat.error.NotFoundException;
import com.mick.mchat.jooq.model.tables.daos.UserDao;
import com.mick.mchat.jooq.model.tables.pojos.User;
import com.mick.mchat.security.AuthenticationService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.field;

@Singleton
public class UserService {
    private final AuthenticationService authenticationService;
    private final UserDao userDao;

    @Inject
    public UserService(
            final AuthenticationService authenticationService,
            final UserDao userDao
    ) {
        this.userDao = userDao;
        this.authenticationService = authenticationService;
    }

    public User createUser(User user) {
        user.setUuid(UUID.randomUUID());
        userDao.insert(user);
        return user;
    }

    public User getUserFromCredentials(String username, String password) throws NotFoundException {
        return userDao
                .configuration()
                .dsl()
                .selectFrom(userDao.getTable())
                .where(field(com.mick.mchat.jooq.model.tables.User.USER.USERNAME).eq(username))
                .and(field(com.mick.mchat.jooq.model.tables.User.USER.PASSWORD).eq(password))
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

    public String login(String username, String password) {
        User user = getUserFromCredentials(username, password);
        return authenticationService.createToken(user);
    }
}
