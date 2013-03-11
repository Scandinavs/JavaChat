package com.chat.connection;

import com.chat.Message;
import com.chat.User;
import org.apache.commons.lang.Validate;

import java.io.IOException;
import java.util.List;

public class GroupUserConnection {
    private final List<Connection> connections;
    private final User user;

    public GroupUserConnection(User mainUser, List<Connection> connections) throws IOException {
        Validate.notNull(connections, "Connections shouldn't be null");

        this.connections = connections;
        this.user = mainUser;
    }

    public User getUser() {
        return user;
    }

    public void write(String message) {
        for (Connection connection : connections) {
            connection.write(message);
        }
    }

    public void write(Message message) {

    }

    public String read() throws IOException {
        return null;
    }

    public void close() {
        for (Connection connection : connections) {
            connection.close();
        }
    }
}
