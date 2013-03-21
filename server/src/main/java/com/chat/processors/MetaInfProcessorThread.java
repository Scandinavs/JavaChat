package com.chat.processors;

import com.chat.connection.Connection;

public class MetaInfProcessorThread extends BaseProcessorThread {

    public MetaInfProcessorThread(Connection connection, String groupId) {
        super(connection, groupId);
    }

    protected void processInput(String inputLine) {
        if (inputLine.startsWith("-chat createGroup")) {

        }
    }
}
