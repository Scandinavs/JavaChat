package com.chat.handlers;


import com.chat.connection.Connection;
import com.chat.model.message.Message;

import java.io.IOException;

public class ServerMetaInfHandler extends ResponseHandler {

    public ServerMetaInfHandler(Connection connection) throws IOException {
        super(connection);
    }

    @Override
    protected Message read() throws IOException {
        return connection.readMetaInf();
    }
}