package com.mick.mchat.handlers.user.in;

import com.mick.mchat.handlers.user.UserMapper;
import com.mick.mchat.handlers.user.UserService;
import com.mick.mchat.handlers.user.out.LoginResponseOut;
import com.mick.mchat.handlers.user.out.UserOut;
import com.mick.mchat.jooq.model.tables.pojos.User;
import com.mick.mchat.security.AuthenticationService;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.WsContextStore;
import com.mick.mchat.websocket.inbound.InMessageHandler;
import com.mick.mchat.websocket.inbound.InMessageType;
import com.mick.mchat.websocket.inbound.MChatMessageHandler;
import com.mick.mchat.websocket.outbound.OutMessageType;
import io.javalin.websocket.WsContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class UserMessageHandler implements InMessageHandler {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final WsContextStore wsContextStore;

    @Inject
    public UserMessageHandler(
            final UserService userService,
            final AuthenticationService authenticationService,
            final WsContextStore wsContextStore) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.wsContextStore = wsContextStore;
    }

    @MChatMessageHandler(
            inType = InMessageType.CREATE_USER,
            outType = OutMessageType.LOGIN_RESPONSE
    )
    public LoginResponseOut createUserAndLogin(CreateUserIn createUserIn, WsContext wsContext) {
        User user = new User();
        user.setUsername(createUserIn.getUsername());
        user.setPassword(createUserIn.getPassword());
        user.setAvatarUrl(createUserIn.getAvatarUrl());
        userService.createUser(user);

        return login(
                new UserLoginIn()
                        .setUsername(createUserIn.getUsername())
                        .setPassword(createUserIn.getPassword()),
                wsContext
        );
    }

    @MChatMessageHandler(
            inType = InMessageType.CURRENT_USER_GET,
            outType = OutMessageType.CURRENT_USER
    )
    public UserOut getActiveUser(CurrentUserGetIn currentUserGetIn, AuthenticationToken authenticationToken) {
        return UserMapper.toOut(
                userService.getUser(authenticationToken.getUserUuid())
        );
    }

    @MChatMessageHandler(
            inType = InMessageType.USER_LOGIN,
            outType = OutMessageType.LOGIN_RESPONSE
    )
    public LoginResponseOut login(UserLoginIn userLoginIn, WsContext wsContext) {
        User user = userService.login(userLoginIn.getUsername(), userLoginIn.getPassword());
        String token = authenticationService.createToken(user);

        wsContextStore.addUserWsContext(user.getUuid(), wsContext);

        return new LoginResponseOut()
                .setAuthenticationToken(token);
    }

    @MChatMessageHandler(
            inType = InMessageType.USERS_GET_ALL,
            outType = OutMessageType.USERS_ALL
    )
    public List<UserOut> getAllUsers(GetAllUsersIn getAllUsersIn, AuthenticationToken authenticationToken) {
        return userService.getAllUsers()
                .stream()
                .map(UserMapper::toOut)
                .collect(Collectors.toList());
    }
}
