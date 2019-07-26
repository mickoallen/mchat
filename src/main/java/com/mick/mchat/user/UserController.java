package com.mick.mchat.user;

import com.mick.mchat.ApiResponseFactory;
import com.mick.mchat.MController;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

@Singleton
public class UserController implements MController {

    private final UserApi userApi;
    private final SecureUserExtractor secureUserExtractor;

    @Inject
    public UserController(final UserApi userApi, final SecureUserExtractor secureUserExtractor) {
        this.userApi = userApi;
        this.secureUserExtractor = secureUserExtractor;
    }

    @Override
    public EndpointGroup getEndpointGroup(){
        return () -> path("/users", () -> {
            get(this::getAllUsers);
            post(this::createUser);

            path("/:id", () -> {
                get(this::getUser);
                put(this::updateUser);
                delete(this::deleteUser);
            });

            path("/login", () -> {
                post(this::login);
            });
        });
    }

    public void getAllUsers(Context context) {
        SecureUser secureUser = secureUserExtractor.getSecureUser(context);
        List<UserApiModel> allUsers = userApi.getAllUsers(secureUser);

        ApiResponseFactory.ok(context, allUsers);
    }

    public void login(Context context) {

    }

    public void createUser(Context context) {

    }

    public void getUser(Context context) {

    }

    public void updateUser(Context context) {

    }

    public void deleteUser(Context context) {

    }
}
