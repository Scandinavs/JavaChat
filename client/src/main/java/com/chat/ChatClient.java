package com.chat;

import com.chat.ui.ConsoleOutput;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private static Logger logger = Logger.getLogger(ChatClient.class);

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        Socket metaInfSocket = null;

        try {
            socket = new Socket("SergeyKlyus-PC", 4444);
            metaInfSocket = new Socket("SergeyKlyus-PC", 4445);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444 or 4445.");
            System.exit(-1);
        }

        final ServerMessageHandler serverMessageHandler = new ServerMessageHandler(socket, new ConsoleOutput());
        serverMessageHandler.start();

        final ServerMetaInfHandler serverMetaInfHandler = new ServerMetaInfHandler(metaInfSocket);
        serverMetaInfHandler.start();

        String fromUser;
        boolean listening = true;

        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             PrintWriter metaInfoPrintWriter = new PrintWriter(metaInfSocket.getOutputStream(), true)) {
            System.out.println("Type your name, please:");
            fromUser = stdIn.readLine();
            metaInfoPrintWriter.println(fromUser);
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