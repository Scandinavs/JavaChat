package com.chat.model;

import com.chat.connection.Connection;
import org.apache.commons.lang.Validate;

import java.util.*;

public final class DataHolder {
    private static final Map<String, List<Connection>> connectionsMap = Collections.synchronizedMap(new HashMap<String, List<Connection>>());
    private static final Map<String, List<Message>> messagesMap = Collections.synchronizedMap(new HashMap<String, List<Message>>());

    public static void addMessage(Message m, String groupId) {
        Validate.notNull(m, "Message shouldn't be null!");
        Validate.notNull(groupId, "GroupId shouldn't be null!");

        List<Message> messages = messagesMap.get(groupId);
        if (messages == null) {
            messages = Collections.synchronizedList(new ArrayList<Message>());
            messages.add(m);
            messagesMap.put(groupId, messages);
        } else {
            messages.add(m);
        }
    }

    public static void addConnection(Connection c, String groupId) {
        Validate.notNull(c, "Connection shouldn't be null!");

        List<Connection> connections = connectionsMap.get(groupId);
        if (connections == null) {
            connections = Collections.synchronizedList(new ArrayList<Connection>());
            connections.add(c);
            connectionsMap.put(groupId, connections);
        } else {
            connections.add(c);
        }
    }

    public static void removeConnection(Connection c, String groupId) {
        Validate.notNull(c, "Connection shouldn't be null!");

        List<Connection> connections = connectionsMap.get(groupId);
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

    public static Map<String, List<Message>> getMessages() {
        return messagesMap;
    }

    public static Map<String, List<Connection>> getConnections() {
        return connectionsMap;
    }
}
