package com.chat;

import com.chat.connection.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    public static final Map<String, Connection> connectionsMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

//        try (Socket socket = serverSocket.accept();
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//                out.println(line + ":Server");
//            }
//        } finally {
//            serverSocket.close();
//        }
        while (true)
            new ServerThread(serverSocket.accept()).start();

    }
}