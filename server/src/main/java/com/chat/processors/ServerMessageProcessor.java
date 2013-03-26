package com.chat.processors;

import com.chat.MessageProcessor;
import com.chat.connection.Connection;
import com.chat.model.*;
import com.chat.model.message.FileMessage;
import com.chat.model.message.MetaInfMessage;
import com.chat.model.message.TextMessage;
import org.apache.log4j.Logger;

public class ServerMessageProcessor implements MessageProcessor {
    private final Logger logger = Logger.getLogger(ServerMessageProcessor.class);

    private Connection connection;
    private String groupId;

    public ServerMessageProcessor(Connection connection, String groupId) {
        this.connection = connection;
        this.groupId = groupId;
    }

    @Override
    public void processTextMessage(TextMessage message) {
        DataHolder.addMessage(message, groupId);
    }

    @Override
    public void processFileMessage(FileMessage message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void processMetaInfMessage(MetaInfMessage message) {
        logger.info("Processing MetaInfMessage:  " + message);
        connection.setUser(message.getCurrentUser());
    }
}
