package com.chat.connection;

import org.apache.commons.lang.Validate;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private final Logger logger = Logger.getLogger("Connection");

    public Connection(Socket socket) throws IOException {
        Validate.notNull(socket, "Socket shouldn't be null");
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void write() {

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
