package com.chat.processors;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;

public abstract class BaseProcessorThread extends Thread {
    protected final Logger logger = Logger.getLogger(this.getClass());

    protected final Connection connection;
    protected String groupId;

    public BaseProcessorThread(Connection connection, String groupId) {
        this.connection = connection;
        this.groupId = groupId;
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = connection.readMessage()) != null && StringUtils.isNotEmpty(inputLine)) {
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
            DataHolder.removeConnection(connection, groupId);
        }
    }

    protected abstract void processInput(String inputLine);
}
