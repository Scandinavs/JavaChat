package com.chat.processors;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;
import com.chat.model.Message;
import com.chat.model.TextMessage;

public class MessageProcessorThread extends BaseProcessorThread {

    public MessageProcessorThread(Connection connection, String groupId) {
        super(connection, groupId);
    }

    @Override
    protected void processInput(String inputLine) {
        DataHolder.addMessage(createMessage(inputLine), groupId);
    }

    private Message createMessage(String inputLine) {
        return new TextMessage(connection, inputLine);
    }
}
