package com.chat.messagesender;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;
import com.chat.model.MetaInfMessage;
import com.chat.model.User;
import org.apache.log4j.Logger;

import java.util.*;

public class MetaInfoSender extends Thread {

    private static final Logger logger = Logger.getLogger(MessageSender.class);

    public MetaInfoSender() {
        setDaemon(true);
    }

    @Override
    public void run() {
        synchronized (DataHolder.getConnections()) {
            List<Connection> connectionList = getAllConnections();
            Set<User> usersOnline = getAllUsers(connectionList);
            int connectionsCount = connectionList.size();
            for (Connection connection : connectionList) {
                try {
                    final MetaInfMessage message = new MetaInfMessage();
                    message.setDescription("Connections count: " + String.valueOf(connectionsCount));
                    message.setUsersOnline(usersOnline);
                    connection.writeMetaInf(message);
                } catch (Exception e) {
                    logger.error(String.format("Message write error for user %s. Ignoring that.", connection.getUser().getName()));
                }
            }
        }
    }

    private Set<User> getAllUsers(List<Connection> connectionList) {
        Set<User> usersOnline = new HashSet<>();
        for (Connection connection : connectionList) {
            usersOnline.add(connection.getUser());
        }
        return usersOnline;
    }

    private List<Connection> getAllConnections() {
        final Map<String, List<Connection>> connections = DataHolder.getConnections();
        List<Connection> allConnectionsList = new ArrayList<>();
        for (List<Connection> connectionList : connections.values()) {
            allConnectionsList.addAll(connectionList);
        }
        return allConnectionsList;
    }
}
