package com.chat.connection;

import com.chat.model.User;

import java.io.IOException;

public interface Connection {
    String readMessage() throws IOException;

    void writeMessage(String message);

    String readMetaInf() throws IOException;

    void writeMetaInf(String message);

    void close();

    User getUser();
}
