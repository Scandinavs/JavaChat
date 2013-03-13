package com.chat;

import com.chat.messagesender.MessageBroker;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {

    private static final Logger logger = Logger.getLogger(ChatServer.class);

    public static void main(String[] args) {
        loadSystemProperties();
        SystemUtils.configureLogger();

        ServerSocket serverSocket = null;
        boolean listening = true;
        final int port = SystemProperties.getServerPort();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.error(String.format("Could not listen on port: %s.", port));
            System.exit(-1);
        }

        logger.info("Waiting for user connections...");

        MessageBroker messageBroker = SystemUtils.startMessageBroker();
        while (listening) {
            SystemUtils.acceptConnection(serverSocket);
        }
        messageBroker.stop();
        IOUtils.closeQuietly(serverSocket);
    }

    private static void loadSystemProperties() {
        try {
            SystemProperties.loadProperties();
        } catch (IOException e) {
            logger.error("Error reading properties file.");
            System.exit(-1);
        }
    }
}