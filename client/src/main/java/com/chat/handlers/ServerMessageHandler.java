package com.chat.handlers;

import com.chat.connection.Connection;
import com.chat.model.Message;

import java.io.IOException;

public class ServerMessageHandler extends ResponseHandler {

    public ServerMessageHandler(Connection connection) throws IOException {
        super(connection);
    }

    @Override
    protected Message read() throws IOException {
        return connection.readMessage();
    }
}