package com.chat.processors;

import com.chat.MessageProcessor;
import com.chat.connection.Connection;
import com.chat.model.*;

public class ServerMessageProcessor implements MessageProcessor {

    private Connection connection;
    private String groupId;

    public ServerMessageProcessor(Connection connection, String groupId) {
        this.connection = connection;
        this.groupId = groupId;
    }

    @Override
    public void processTextMessage(TextMessage message) {
//        message.setFrom(connection.getUser());
        DataHolder.addMessage(message, groupId);
    }

    @Override
    public void processFileMessage(FileMessage message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void processMetaInfMessage(MetaInfMessage message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
