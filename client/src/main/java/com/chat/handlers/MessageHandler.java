package com.chat.handlers;

import com.chat.ClientMessageProcessor;
import com.chat.connection.Connection;
import com.chat.ui.ConsoleOutput;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MessageHandler extends Thread {

    private final Logger logger = Logger.getLogger(this.getClass());
    private static boolean listening = true;
    protected final Connection connection;
    protected ClientMessageProcessor messageProcessor;

    public MessageHandler(Connection connection) throws IOException {
        this.connection = connection;
        this.messageProcessor = new ClientMessageProcessor(new ConsoleOutput());
    }

    @Override
    public void run() {
        try {
            while (listening) {
                connection.readMessage().process(messageProcessor);
            }
        } catch (IOException e) {
            logger.error("Error reading data.", e);
        } finally {
            connection.close();
        }
    }

    public void stopHandler() {
        listening = false;
    }
}
