package com.chat.connection;

import com.chat.Message;
import com.chat.User;
import org.apache.commons.lang.Validate;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleUserConnection implements Connection {
    private final User user;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private final Logger logger = Logger.getLogger("Connection");

    public SingleUserConnection(Socket socket, User user) throws IOException {
        Validate.notNull(socket, "Socket shouldn't be null");
        Validate.notNull(user, "User shouldn't be null");

        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void write(String message) {
        out.println(message);
    }

    @Override
    public void write(Message message) {
        out.println(message.message);
    }

    public String read() throws IOException {
        return in.readLine();
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error closing connection!");
        }
    }
}
