package com.chat.handlers;

import com.chat.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerMetaInfHandler extends ResponseHandler {

    public ServerMetaInfHandler(Connection connection) throws IOException {
        super(connection);
    }

    @Override
    protected String read() throws IOException {
        return connection.readMetaInf();
    }

    @Override
    protected void processInput(String message) throws IOException {
        if (message.equals("Connections count: 3")) {
            System.out.println(message);
        }
    }
}