package com.chat;

import com.chat.connection.Connection;
import com.chat.connection.SocketConnection;
import com.chat.messagesender.MessageBroker;
import com.chat.messagesender.MessageSender;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatServer {

    private static final Logger logger = Logger.getLogger("ChatServer");

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            logger.warning("Could not listen on port: 4444.");
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
            DataHolder.INSTANCE.addConnection(connection);
            new ServerThread(connection).start();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error creating connection!", e);
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