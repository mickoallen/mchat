package com.mick.mchat;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.mick.mchat.handlers.chat.ChatMessageHandler;
import com.mick.mchat.handlers.conversation.ConversationMessageHandler;
import com.mick.mchat.handlers.user.in.UserMessageHandler;
import com.mick.mchat.jooq.model.tables.daos.ConversationDao;
import com.mick.mchat.jooq.model.tables.daos.MessageDao;
import com.mick.mchat.jooq.model.tables.daos.UserConversationDao;
import com.mick.mchat.jooq.model.tables.daos.UserDao;
import com.mick.mchat.websocket.inbound.InMessageHandler;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import javax.inject.Named;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * Magic, bitches.
 */
public class MChatModule extends AbstractModule {

    @Override
    protected void configure() {
        /* Register all the message handlers here */
        Set<Class<? extends InMessageHandler>> messageHandlers = Set.of(
                UserMessageHandler.class,
                ConversationMessageHandler.class,
                ChatMessageHandler.class
        );

        Multibinder<InMessageHandler> messageHandlerMultibinder = Multibinder.newSetBinder(binder(), InMessageHandler.class);

        messageHandlers.forEach(messageHandler -> messageHandlerMultibinder.addBinding().to(messageHandler));
    }

    @Provides
    @Singleton
    public Configuration provideJooqConfiguration(Config config) throws URISyntaxException {

        URI dbUri = new URI(config.getString("db.url"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];

        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        //add ssl if not localhost
        if(!"localhost".equals(dbUri.getHost())){
            dbUrl = dbUrl + "?sslmode=require";
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dbUrl);
        hikariConfig.setMinimumIdle(config.getInt("db.min_pool"));
        hikariConfig.setMaximumPoolSize(config.getInt("db.max_pool"));
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setConnectionTestQuery("SELECT 1;");
        hikariConfig.setConnectionTimeout(config.getInt("db.connection_timeout"));

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        return new DefaultConfiguration()
                .set(SQLDialect.POSTGRES)
                .set(hikariDataSource);
    }

    @Provides
    @Singleton
    public Config provideTypesafeConfig() {
        return ConfigFactory.load();
    }

    @Provides
    @Singleton
    public UserDao provideDao(Configuration configuration) {
        return new UserDao(configuration);
    }

    @Provides
    @Singleton
    public ConversationDao provideConversationDao(Configuration configuration) {
        return new ConversationDao(configuration);
    }

    @Provides
    @Singleton
    public MessageDao provideMessageDao(Configuration configuration) {
        return new MessageDao(configuration);
    }

    @Provides
    @Singleton
    public UserConversationDao provideUserConversationDao(Configuration configuration) {
        return new UserConversationDao(configuration);
    }

    @Provides
    @Singleton
    @Named("password.salt")
    public String providePasswordSalt(Config config) {
        return config.getString("password.salt");
    }
}
