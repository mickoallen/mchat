package com.mick.mchat;

import com.mick.mchat.user.SecureUserExtractor;
import com.mick.mchat.user.UserApi;
import com.mick.mchat.user.UserController;
import com.mick.mchat.user.UserRepository;
import com.mick.mchat.websocket.MChatWsHandler;
import com.mick.mchat.websocket.WsContextStore;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.websocket.WsContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Main {

    private static Map<String, TestMessage> testMessages = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Javalin javalin = Javalin.create(config -> {
            config.addStaticFiles("/home/mick/MickProjects/mchat/src/main/resources/public", Location.EXTERNAL);
        }).ws("/docs/:doc-id", ws -> {
            ws.onConnect(ctx -> {
                if (getTestMessage(ctx) == null) {
                    createTestMessage(ctx);
                }
                getTestMessage(ctx).clients.add(ctx);
                ctx.send(getTestMessage(ctx).doc);
            });
            ws.onMessage(ctx -> {
                getTestMessage(ctx).doc = ctx.message();
                getTestMessage(ctx).clients.stream().filter(c -> c.session.isOpen()).forEach(s -> {
                    s.send(getTestMessage(ctx).doc);
                });
            });
            ws.onClose(ctx -> {
                getTestMessage(ctx).clients.remove(ctx);
            });
        }).start(7070);


        UserRepository userRepository = new UserRepository();
        SecureUserExtractor secureUserExtractor = new SecureUserExtractor(userRepository);
        //controllers
        List<MController> controllers = List.of(
                new UserController(new UserApi(userRepository), secureUserExtractor)
        );

        MChatWsHandler mChatWsHandler = new MChatWsHandler(new WsContextStore(), secureUserExtractor);

        //wscontext
        javalin.ws("/api/ws", ws -> {
            ws.onConnect(mChatWsHandler);
            ws.onClose(mChatWsHandler);
            ws.onError(mChatWsHandler);
            ws.onMessage(mChatWsHandler);
        });

        //add api routes
        controllers.forEach(mController ->
                javalin.routes(
                        () -> path("/api", mController.getEndpointGroup())
                )
        );

        javalin.routes(() ->
                get("/api/health", (context) -> {
                    context.result("All is good").status(200);
                })
        );
    }

    private static TestMessage getTestMessage(WsContext ctx) {
        return testMessages.get(ctx.pathParam("doc-id"));
    }

    private static void createTestMessage(WsContext ctx) {
        testMessages.put(ctx.pathParam("doc-id"), new TestMessage());
    }
}
