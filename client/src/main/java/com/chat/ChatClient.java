package com.chat;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;

        try {
            socket = new Socket("SergeyKlyus-PC", 4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        final ServerMessageHandler serverMessageHandler = new ServerMessageHandler(socket);
        serverMessageHandler.start();

        String fromUser;
        boolean listening = true;

        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            while (listening) {
                fromUser = stdIn.readLine();
                if (StringUtils.isNotBlank(fromUser)) {
                    out.println(fromUser);
                }
            }
        }

        serverMessageHandler.stopHandler();
        socket.close();
    }
}