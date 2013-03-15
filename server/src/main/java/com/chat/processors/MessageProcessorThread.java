package com.chat.processors;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;
import com.chat.model.Message;

public class MessageProcessorThread extends BaseProcessorThread {

    public MessageProcessorThread(Connection connection) {
        super(connection);
    }

    @Override
    protected void processInput(String inputLine) {
        DataHolder.addMessage(createMessage(inputLine), connection.getGroup());
    }

    private Message createMessage(String inputLine) {
        return new Message(connection, inputLine);
    }
}
