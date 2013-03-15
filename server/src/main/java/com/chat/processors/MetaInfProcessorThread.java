package com.chat.processors;

import com.chat.connection.Connection;

public class MetaInfProcessorThread extends BaseProcessorThread {

    public MetaInfProcessorThread(Connection connection) {
        super(connection);
    }

    protected void processInput(String inputLine) {
        if (inputLine.startsWith("-chat createGroup")) {

        }
    }
}
