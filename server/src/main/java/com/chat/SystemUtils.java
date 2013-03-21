package com.chat;

import com.chat.connection.Connection;
import com.chat.connection.UserConnection;
import com.chat.messagesender.MessageBroker;
import com.chat.messagesender.MessageSender;
import com.chat.messagesender.MetaInfoSender;
import com.chat.model.Constants;
import com.chat.model.DataHolder;
import com.chat.model.User;
import com.chat.processors.MessageProcessorThread;
import com.chat.processors.MetaInfProcessorThread;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class SystemUtils {

    private static final Logger logger = Logger.getLogger(SystemUtils.class);

    public static void acceptConnection(ServerSocket messagesSocket, ServerSocket serviceServerSocket) {
        Socket socket = null;
        Socket serviceSocket = null;
        Connection connection = null;
        try {
            socket = messagesSocket.accept();
            serviceSocket = serviceServerSocket.accept();
            connection = createConnection(socket, serviceSocket);
            DataHolder.addConnection(connection, Constants.DEFAULT_GROUP);
            new MessageProcessorThread(connection, Constants.DEFAULT_GROUP).start();
            new MetaInfProcessorThread(connection, Constants.DEFAULT_GROUP).start();
        } catch (IOException e) {
            logger.error("Error creating connection!", e);
            IOUtils.closeQuietly(socket);
            IOUtils.closeQuietly(serviceSocket);
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

    private static Connection createConnection(Socket socket, Socket serviceSocket) throws IOException {
        User user = getUser(serviceSocket);
        return new UserConnection(socket, serviceSocket, user);
    }

    private static User getUser(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        if ((line = in.readLine()) != null) {
            logger.info(String.format("User %s connected!", line));
            return new User(line);
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
