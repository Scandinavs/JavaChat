package com.chat;

import com.chat.connection.Connection;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public class ServerThread extends Thread {

    private final Connection connection;

    public ServerThread(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = connection.read()) != null && StringUtils.isNotEmpty(inputLine)) {
                DataHolder.INSTANCE.addMessage(createMessage(inputLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    private Message createMessage(String inputLine) {
        return new Message(connection, inputLine);
    }
}
