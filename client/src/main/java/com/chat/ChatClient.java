package com.chat;

import com.chat.handlers.ServerMessageHandler;
import com.chat.handlers.ServerMetaInfHandler;
import com.chat.ui.ConsoleOutput;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private static Logger logger = Logger.getLogger(ChatClient.class);

    public static void main(String[] args) {
        Socket socket = null;
        Socket metaInfSocket = null;

        try {
            socket = new Socket("SergeyKlyus-PC", 4444);
            metaInfSocket = new Socket("SergeyKlyus-PC", 4445);
        } catch (IOException e) {
            logger.error("Could not listen on port: 4444 or 4445.", e);
            System.exit(-1);
        }

        ServerMessageHandler serverMessageHandler = null;
        try {
            serverMessageHandler = new ServerMessageHandler(socket, new ConsoleOutput());
            serverMessageHandler.start();
        } catch (IOException e) {
            logger.error("Error creating ServerMessageHandler.", e);
            System.exit(-1);
        }
        ServerMetaInfHandler serverMetaInfHandler = null;
        try {
            serverMetaInfHandler = new ServerMetaInfHandler(metaInfSocket);
            serverMetaInfHandler.start();
        } catch (IOException e) {
            logger.error("Error creating ServerMessageHandler.", e);
            System.exit(-1);
        }

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
        } catch (IOException e) {
            logger.error(e);
        }

        serverMessageHandler.stopHandler();
        serverMetaInfHandler.stopHandler();
        IOUtils.closeQuietly(socket);
        IOUtils.closeQuietly(metaInfSocket);
    }
}