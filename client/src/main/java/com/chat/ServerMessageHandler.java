package com.chat;

import com.chat.ui.OutputInterface;

import java.io.IOException;
import java.net.Socket;

public class ServerMessageHandler extends ResponseHandler {

    private final OutputInterface out;

    public ServerMessageHandler(Socket socket, OutputInterface out) throws IOException {
        super(socket);
        this.out = out;
    }

    @Override
    protected void processInput(String message) throws IOException {
        out.write(message);
    }
}