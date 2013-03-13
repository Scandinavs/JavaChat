package com.chat.processors;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;

public class ServiceThread extends BaseServerThread {

    public ServiceThread(Connection connection) {
        super(connection, DataHolder.DEFAULT_GROUP);
    }

    protected void processInput(String inputLine) {
        if (inputLine.startsWith("-chat createGroup")) {

        }
    }
}
