package com.mick.mchat.handlers.user.in;

import com.mick.mchat.handlers.user.UserMapper;
import com.mick.mchat.handlers.user.UserService;
import com.mick.mchat.handlers.user.out.LoginResponseOut;
import com.mick.mchat.handlers.user.out.UserOut;
import com.mick.mchat.jooq.model.tables.pojos.User;
import com.mick.mchat.security.AuthenticationToken;
import com.mick.mchat.websocket.inbound.InMessageHandler;
import com.mick.mchat.websocket.inbound.InMessageType;
import com.mick.mchat.websocket.inbound.MChatMessageHandler;
import com.mick.mchat.websocket.outbound.OutMessageType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserMessageHandler implements InMessageHandler {
    private final UserService userService;

    @Inject
    public UserMessageHandler(
            final UserService userService
    ) {
        this.userService = userService;
    }

    @MChatMessageHandler(
            inType = InMessageType.USER_LOGIN,
            outType = OutMessageType.LOGIN_RESPONSE
    )
    public LoginResponseOut login(UserLoginIn userLoginIn) {
        return new LoginResponseOut()
                .setAuthenticationToken(
                        userService.login(userLoginIn.getUsername(), userLoginIn.getPassword())
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
            inType = InMessageType.CREATE_USER,
            outType = OutMessageType.LOGIN_RESPONSE
    )
    public LoginResponseOut createUserAndLogin(CreateUserIn createUserIn) {
        User user = new User();
        user.setUsername(createUserIn.getUsername());
        user.setPassword(createUserIn.getPassword());

        userService.createUser(user);

        return login(
                new UserLoginIn()
                        .setUsername(createUserIn.getUsername())
                        .setPassword(createUserIn.getPassword())
        );
    }
}
