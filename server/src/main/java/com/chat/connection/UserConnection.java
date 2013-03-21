package com.chat.connection;

import com.chat.model.User;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserConnection implements Connection {
    private final User user;
    private final Socket socket;
    private final Socket serviceSocket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final PrintWriter metaInfoOut;

    public UserConnection(Socket socket, Socket serviceSocket, User user) throws IOException {
        Validate.notNull(socket, "Socket shouldn't be null");
        Validate.notNull(serviceSocket, "ServiceSocket shouldn't be null");
        Validate.notNull(user, "User shouldn't be null");

        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.metaInfoOut = new PrintWriter(serviceSocket.getOutputStream(), true);
        this.user = user;
        this.serviceSocket = serviceSocket;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void writeMessage(String message) {
        out.println(message);
    }

    @Override
    public String readMetaInf() throws IOException {
        return null;
    }

    @Override
    public void writeMetaInf(String message) {
        metaInfoOut.println(message);
    }

    @Override
    public String readMessage() throws IOException {
        return in.readLine();
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(socket);
        IOUtils.closeQuietly(serviceSocket);
    }
}
