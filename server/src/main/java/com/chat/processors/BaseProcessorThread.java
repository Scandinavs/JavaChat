package com.chat.processors;

import com.chat.connection.Connection;
import com.chat.model.DataHolder;
import com.chat.model.message.Message;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.SocketException;

public abstract class BaseProcessorThread extends Thread {
    protected final Logger logger = Logger.getLogger(this.getClass());

    protected final Connection connection;
    protected String groupId;
    private ServerMessageProcessor messageProcessor;

    public BaseProcessorThread(Connection connection, String groupId) {
        this.connection = connection;
        this.groupId = groupId;
        this.messageProcessor = new ServerMessageProcessor(connection, groupId);
    }

    @Override
    public void run() {
        try {
            Message message;
            while ((message = read()) != null) {
                message.process(messageProcessor);
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

    protected abstract Message read() throws IOException;
}
