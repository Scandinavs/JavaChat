package com.chat.processors;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;
import com.chat.model.Message;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMessageThread extends BaseServerThread {

    public ServerMessageThread(Connection connection, String groupId) {
        super(connection, groupId);
    }

    @Override
    protected void processInput(String inputLine) {
        DataHolder.addMessage(createMessage(inputLine), groupId);
    }

    private Message createMessage(String inputLine) {
        return new Message(connection, inputLine);
    }
}
