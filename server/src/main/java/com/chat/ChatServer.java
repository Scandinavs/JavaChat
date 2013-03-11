package com.chat;

import com.chat.connection.Connection;
import com.chat.connection.SingleUserConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
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

        MessageBroker.start();
        while (listening) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                Connection connection = createConnection(socket);
                DataHolder.INSTANCE.addConnection(connection);
                new ServerThread(connection).start();
            } catch (IOException e) {
                e.printStackTrace();
                logger.warning("Error creating connection!");
                closeSocket(socket);
            }
        }
        MessageBroker.stop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.warning("Error closing server socket connection!");
        }
    }

    private static void closeSocket(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static Connection createConnection(Socket socket) throws IOException {
        User user = getUser(socket);
        return new SingleUserConnection(socket, user);
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