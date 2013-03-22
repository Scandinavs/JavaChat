package com.chat.messagesender;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;
import com.chat.model.MetaInfMessage;

import java.util.List;
import java.util.Map;

public class MetaInfoSender extends Thread {

    public MetaInfoSender() {
        setDaemon(true);
    }

    @Override
    public void run() {
        final Map<String, List<Connection>> connections = DataHolder.getConnections();
        int connectionsCount = 0;
        for (List<Connection> connectionList : connections.values()) {
            connectionsCount += connectionList.size();
        }

        for (List<Connection> connectionList : connections.values()) {
            for (Connection connection : connectionList) {
                final MetaInfMessage message = new MetaInfMessage();
                message.setDescription("Connections count: " + String.valueOf(connectionsCount));
                connection.writeMetaInf(message);
            }
        }
    }
}
