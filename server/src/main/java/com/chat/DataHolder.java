package com.chat;

import com.chat.connection.Connection;
import org.apache.commons.lang.Validate;

import java.util.*;

public enum DataHolder {
    INSTANCE;
    public static final String DEFAULT_GROUP = "default";

    private static final Map<String, List<Connection>> connectionsMap = Collections.synchronizedMap(new HashMap<String, List<Connection>>());
    private static final Map<String, List<Message>> messagesMap = Collections.synchronizedMap(new HashMap<String, List<Message>>());

    public List<Message> getDefaultGroupMessages() {
        return messagesMap.get(DEFAULT_GROUP);
    }

    public void addMessage(Message m) {
        Validate.notNull(m, "Message shouldn't be null!");

        List<Message> messages = messagesMap.get(m.getGroupId());
        if (messages == null) {
            messages = new ArrayList<>();
            messages.add(m);
            messagesMap.put(m.getGroupId(), messages);
        } else {
            messages.add(m);
        }
    }

    public void removeMessage(Message m) {
        Validate.notNull(m, "Message shouldn't be null!");

        List<Message> messages = messagesMap.get(m.getGroupId());
        if (messages != null) {
            messages.remove(m);
        }
    }

    public void clearMessagesList() {
        messagesMap.clear();
    }

    public void addConnection(Connection c) {
        Validate.notNull(c, "Connection shouldn't be null!");

        List<Connection> connections = connectionsMap.get(c.getGroup());
        if (connections == null) {
            connections = new ArrayList<>();
            connections.add(c);
            connectionsMap.put(c.getGroup(), connections);
        } else {
            connections.add(c);
        }
    }

    public void removeConnection(Connection c) {
        Validate.notNull(c, "Connection shouldn't be null!");

        List<Connection> connections = connectionsMap.get(c.getGroup());
        if (connections != null) {
            connections.remove(c);
        }
    }

    public List<Connection> getDefaultGroupConnections() {
        return connectionsMap.get(DEFAULT_GROUP);
    }

    public List<Message> getGroupMessages(String group) {
        return messagesMap.get(group);
    }

    public List<Connection> getGroupConnections(String group) {
        return connectionsMap.get(group);
    }
}
