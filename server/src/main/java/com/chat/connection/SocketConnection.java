package com.chat.connection;

import com.chat.DataHolder;
import com.chat.Message;
import com.chat.User;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketConnection implements Connection {
    private final User user;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private String group;

    public SocketConnection(Socket socket, User user, String group) throws IOException {
        Validate.notNull(socket, "Socket shouldn't be null");
        Validate.notNull(user, "User shouldn't be null");

        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.user = user;
        this.group = group;
    }

    public SocketConnection(Socket socket, User user) throws IOException {
        this(socket, user, DataHolder.DEFAULT_GROUP);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public void write(String message) {
        out.println(message);
    }

    @Override
    public String read() throws IOException {
        return in.readLine();
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(socket);
    }
}
