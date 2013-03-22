package com.chat.handlers;

import com.chat.ClientMessageProcessor;
import com.chat.connection.Connection;
import com.chat.model.Message;
import com.chat.ui.ConsoleOutput;
import org.apache.log4j.Logger;

import java.io.IOException;

public abstract class ResponseHandler extends Thread {

    private final Logger logger = Logger.getLogger(this.getClass());
    private static boolean listening = true;
    protected final Connection connection;
    protected ClientMessageProcessor messageProcessor;

    public ResponseHandler(Connection connection) throws IOException {
        this.connection = connection;
        this.messageProcessor = new ClientMessageProcessor(new ConsoleOutput());
    }

    @Override
    public void run() {
        try {
            while (listening) {
                read().process(messageProcessor);
            }
        } catch (IOException e) {
            logger.error("Error reading data.", e);
        } finally {
            connection.close();
        }
    }

    protected abstract Message read() throws IOException;

    public void stopHandler() {
        listening = false;
    }
}
