package com.chat;

import com.chat.connection.Connection;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum DataHolder {
    INSTANCE;
    private static final List<Connection> connections = Collections.synchronizedList(new ArrayList<Connection>());
    private static final List<Message> messages = Collections.synchronizedList(new ArrayList<Message>());

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message m) {
        Validate.notNull(m, "Message shouldn't be null!");

        messages.add(m);
    }

    public void removeMessage(Message m) {
        Validate.notNull(m, "Message shouldn't be null!");

        messages.remove(m);
    }

    public void clearMessagesList() {
        messages.clear();
    }

    public void addConnection(Connection c) {
        Validate.notNull(c, "Connection shouldn't be null!");

        connections.add(c);
    }

    public void removeConnection(Connection c) {
        Validate.notNull(c, "Connection shouldn't be null!");

        connections.remove(c);
    }

    public List<Connection> getConnections() {
        return connections;
    }
}
