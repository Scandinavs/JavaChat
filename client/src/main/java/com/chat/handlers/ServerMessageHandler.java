package com.chat.handlers;

import com.chat.connection.Connection;
import com.chat.ui.OutputInterface;

import java.io.IOException;

public class ServerMessageHandler extends ResponseHandler {

    private final OutputInterface out;

    public ServerMessageHandler(Connection connection, OutputInterface out) throws IOException {
        super(connection);
        this.out = out;
    }

    @Override
    protected String read() throws IOException {
        return connection.readMessage();
    }

    @Override
    protected void processInput(String message) throws IOException {
        out.write(message);
    }
}