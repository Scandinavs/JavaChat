package com.chat.handlers;

import com.chat.connection.Connection;
import org.apache.log4j.Logger;

import java.io.IOException;

public abstract class ResponseHandler extends Thread {

    private static Logger logger = Logger.getLogger(ResponseHandler.class);
    protected final Connection connection;
    private static boolean listening = true;

    public ResponseHandler(Connection connection) throws IOException {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            while (listening) {
                processInput(read());
            }
        } catch (IOException e) {
            logger.error("Error reading data.", e);
        } finally {
            connection.close();
        }
    }

    protected abstract String read() throws IOException;

    protected abstract void processInput(String message) throws IOException;

    public void stopHandler() {
        listening = false;
    }
}
