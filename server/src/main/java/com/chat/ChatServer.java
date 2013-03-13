package com.chat;

import com.chat.connection.Connection;
import com.chat.connection.SocketConnection;
import com.chat.messagesender.MessageBroker;
import com.chat.messagesender.MessageSender;
import com.chat.model.DataHolder;
import com.chat.model.User;
import com.chat.processors.ServerMessageThread;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {

    private static final Logger logger = Logger.getLogger(ChatServer.class);

    public static void main(String[] args) {
        try {
            SystemProperties.loadProperties();
        } catch (IOException e) {
            logger.error("Error reading properties file.");
            System.exit(-1);
        }

        ServerSocket serverSocket = null;
        boolean listening = true;
        final int port = SystemProperties.getServerPort();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.error(String.format("Could not listen on port: %s.", port));
            System.exit(-1);
        }

        MessageBroker messageBroker = startMessageBroker();
        while (listening) {
            acceptConnection(serverSocket);
        }
        messageBroker.stop();
        IOUtils.closeQuietly(serverSocket);
    }

    private static void acceptConnection(ServerSocket serverSocket) {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            Connection connection = createConnection(socket);
            DataHolder.addConnection(connection);
            new ServerMessageThread(connection, DataHolder.DEFAULT_GROUP).start();
        } catch (IOException e) {
            logger.error("Error creating connection!", e);
            IOUtils.closeQuietly(socket);
        }
    }

    private static MessageBroker startMessageBroker() {
        MessageBroker messageBroker = new MessageBroker(new MessageSender());
        messageBroker.start();
        return messageBroker;
    }

    private static Connection createConnection(Socket socket) throws IOException {
        User user = getUser(socket);
        return new SocketConnection(socket, user);
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
}