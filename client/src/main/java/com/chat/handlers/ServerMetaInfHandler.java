package com.chat.handlers;

import java.io.IOException;
import java.net.Socket;

public class ServerMetaInfHandler extends ResponseHandler {

    public ServerMetaInfHandler(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected void processInput(String message) throws IOException {
        if (message.equals("Connections count: 3")) {
            System.out.println(message);
        }
    }
}