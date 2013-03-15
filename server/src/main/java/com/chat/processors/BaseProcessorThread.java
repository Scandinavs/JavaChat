package com.chat.processors;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;

public abstract class BaseProcessorThread extends Thread {
    protected static final Logger logger = Logger.getLogger("ServerThread");

    protected final Connection connection;

    public BaseProcessorThread(Connection connection) {
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
            logger.error(message, e);
        } catch (IOException e) {
            String message = "Reading error.";
            logger.error(message, e);
        } finally {
            String message = String.format("Closing connection for user %s!", connection.getUser().getName());
            logger.info(message);
            connection.close();
            DataHolder.removeConnection(connection);
        }
    }

    protected abstract void processInput(String inputLine);
}
