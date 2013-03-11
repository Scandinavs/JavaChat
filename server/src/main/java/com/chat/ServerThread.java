package com.chat;

import com.chat.connection.Connection;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    private static final Logger logger = Logger.getLogger("ServerThread");

    private final Connection connection;

    public ServerThread(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = connection.read()) != null && StringUtils.isNotEmpty(inputLine)) {
                processInput(inputLine);
            }
        } catch (SocketException e) {
            String message = "Socket exception!";
            logger.log(Level.WARNING, message, e);
        } catch (IOException e) {
            String message = "Reading error.";
            logger.log(Level.WARNING, message, e);
        } finally {
            String message = String.format("Closing connection for user %s!", connection.getUser().getName());
            logger.info(message);
            connection.close();
            DataHolder.INSTANCE.removeConnection(connection);
        }
    }

    private void processInput(String inputLine) {

        DataHolder.INSTANCE.addMessage(createMessage(inputLine));
    }

    private Message createMessage(String inputLine) {
        return new Message(connection, inputLine);
    }
}
