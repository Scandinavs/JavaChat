package com.chat;

import com.chat.connection.BaseConnection;
import com.chat.connection.Connection;
import com.chat.handlers.ServerMessageHandler;
import com.chat.handlers.ServerMetaInfHandler;
import com.chat.model.message.MetaInfMessage;
import com.chat.model.message.TextMessage;
import com.chat.model.user.User;
import com.chat.model.user.UserStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatClient {
    private static Logger logger = Logger.getLogger(ChatClient.class);

    public static void main(String[] args) {
        configureLogger();
        Connection connection = null;
        try {
            connection = new BaseConnection("SergeyKlyus-PC", 4444, 4445);
        } catch (IOException e) {
            logger.error("Could not listen on port: 4444 or 4445.", e);
            System.exit(-1);
        }

        ServerMessageHandler serverMessageHandler = null;
        ServerMetaInfHandler serverMetaInfHandler = null;
        try {
            serverMessageHandler = new ServerMessageHandler(connection);
            serverMessageHandler.start();
            serverMetaInfHandler = new ServerMetaInfHandler(connection);
            serverMetaInfHandler.start();
        } catch (IOException e) {
            logger.error("Error creating ServerMessageHandler or ServerMessageHandler.", e);
            System.exit(-1);
        }

        String fromUser;
        boolean listening = true;

        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Type your name, please:");
            fromUser = stdIn.readLine();
            DataHolder.setCurrentUser(new User(fromUser));
            connection.writeMetaInf(new MetaInfMessage(DataHolder.getCurrentUser()));
            while (listening) {
                fromUser = stdIn.readLine();
                if (fromUser.equals("-onlineUsers")) {
                    printOnlineUsers();
                } else if (fromUser.startsWith("-status ")) {
                    changeStatus(connection, fromUser);
                } else if (StringUtils.isNotBlank(fromUser)) {
                    connection.writeMessage(new TextMessage(DataHolder.getCurrentUser(), fromUser));
                }
            }
        } catch (IOException e) {
            logger.error("Error reading.", e);
        }

        serverMessageHandler.stopHandler();
        serverMetaInfHandler.stopHandler();
        connection.close();
    }

    private static void changeStatus(Connection connection, String fromUser) throws IOException {
        String status = fromUser.substring(8);
        final UserStatus userStatus = UserStatus.getUserStatus(status);
        if (userStatus != null) {
            DataHolder.getCurrentUser().setStatus(userStatus);
            connection.writeMetaInf(new MetaInfMessage(DataHolder.getCurrentUser()));
        }
    }

    private static void printOnlineUsers() {
        for (User user : DataHolder.getUsersOnline()) {
            System.out.println(String.format("---%s(%s)---", user.getName(), user.getStatus().getStatus()));
        }
    }

    public static void configureLogger() {
        ConsoleAppender console = new ConsoleAppender();
        String PATTERN = "%d [%p|%c|%C{1}] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.ALL);
        console.activateOptions();

        FileAppender fa = new FileAppender();
        fa.setName("FileLogger");
        fa.setFile("../logfile.log");
        fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        fa.setThreshold(Level.ALL);
        fa.setAppend(true);
        fa.activateOptions();

        Logger.getRootLogger().addAppender(console);
        Logger.getRootLogger().addAppender(fa);
    }
}