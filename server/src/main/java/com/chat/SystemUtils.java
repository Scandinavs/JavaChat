package com.chat;

import com.chat.connection.BaseConnection;
import com.chat.connection.Connection;
import com.chat.messagesender.MessageBroker;
import com.chat.messagesender.MessageSender;
import com.chat.messagesender.MetaInfoSender;
import com.chat.model.*;
import com.chat.model.message.MetaInfMessage;
import com.chat.model.user.User;
import com.chat.processors.MessagesProcessorThread;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class SystemUtils {

    private static final Logger logger = Logger.getLogger(SystemUtils.class);

    public static void acceptConnection(ServerSocket messagesSocket) {
        Socket socket = null;
        Connection connection = null;
        try {
            socket = messagesSocket.accept();
            connection = createConnection(socket);
            DataHolder.addConnection(connection, Constants.DEFAULT_GROUP);
            new MessagesProcessorThread(connection, Constants.DEFAULT_GROUP).start();
        } catch (IOException e) {
            logger.error("Error creating connection!", e);
            IOUtils.closeQuietly(socket);
            if (connection != null) {
                DataHolder.removeConnection(connection, Constants.DEFAULT_GROUP);
            }
        }
    }

    public static MessageBroker startMessageBroker() {
        MessageBroker messageBroker = new MessageBroker(Arrays.<Runnable>asList(new MessageSender(), new MetaInfoSender()));
        messageBroker.start();
        return messageBroker;
    }

    private static Connection createConnection(Socket socket) throws IOException {
        final Connection connection = new BaseConnection(socket);
        User user = getUser(connection);
        connection.setUser(user);
        return connection;
    }

    private static User getUser(Connection connection) throws IOException {
        MetaInfMessage message;
        if ((message = (MetaInfMessage) connection.readMessage()) != null) {
            logger.info(String.format("User %s connected!", message.getCurrentUser().getName()));
            return message.getCurrentUser();
        }
        return null;
    }

    public static void configureLogger() {
        ConsoleAppender console = new ConsoleAppender();
        String PATTERN = "%d [%p|%c|%C{1}] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.ALL);
        console.activateOptions();

        FileAppender fa = new FileAppender();
        fa.setName("FileLogger");
        fa.setFile(SystemProperties.getLogfilePath());
        fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        fa.setThreshold(Level.ALL);
        fa.setAppend(true);
        fa.activateOptions();

        Logger.getRootLogger().addAppender(console);
        Logger.getRootLogger().addAppender(fa);
    }
}
