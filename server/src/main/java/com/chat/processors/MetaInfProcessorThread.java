package com.chat.processors;

import com.chat.connection.Connection;
import com.chat.model.Message;

import java.io.IOException;

public class MetaInfProcessorThread extends BaseProcessorThread {

    public MetaInfProcessorThread(Connection connection, String groupId) {
        super(connection, groupId);
    }

    @Override
    protected Message read() throws IOException {
        return connection.readMetaInf();
    }
}
