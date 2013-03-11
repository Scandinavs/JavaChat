package com.chat.model;

import com.chat.connection.Connection;
import com.chat.model.Message;
import org.apache.commons.lang.Validate;

import java.util.*;

public class DataHolder {
    public static final String DEFAULT_GROUP = "default";

    private static final Map<String, List<Connection>> connectionsMap = Collections.synchronizedMap(new HashMap<String, List<Connection>>());
    private static final Map<String, List<Message>> messagesMap = Collections.synchronizedMap(new HashMap<String, List<Message>>());

    public static void addMessage(Message m) {
        Validate.notNull(m, "Message shouldn't be null!");

        List<Message> messages = messagesMap.get(m.getGroupId());
        if (messages == null) {
            messages = Collections.synchronizedList(new ArrayList<Message>());
            messages.add(m);
            messagesMap.put(m.getGroupId(), messages);
        } else {
            messages.add(m);
        }
    }

    public static void addConnection(Connection c) {
        Validate.notNull(c, "Connection shouldn't be null!");

        List<Connection> connections = connectionsMap.get(c.getGroup());
        if (connections == null) {
            connections = Collections.synchronizedList(new ArrayList<Connection>());
            ;
            connections.add(c);
            connectionsMap.put(c.getGroup(), connections);
        } else {
            connections.add(c);
        }
    }

    public static void removeConnection(Connection c) {
        Validate.notNull(c, "Connection shouldn't be null!");

        List<Connection> connections = connectionsMap.get(c.getGroup());
        if (connections != null) {
            connections.remove(c);
        }
    }

    public static List<Message> getGroupMessages(String group) {
        return messagesMap.get(group);
    }

    public static List<Connection> getGroupConnections(String group) {
        return connectionsMap.get(group);
    }
}
