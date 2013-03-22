package com.chat.connection;

import com.chat.model.Message;
import com.chat.model.User;

import java.io.IOException;

public interface Connection {
    Message readMessage() throws IOException;

    void writeMessage(Message message) throws IOException;

    Message readMetaInf() throws IOException;

    void writeMetaInf(Message message) throws IOException;

    void close();

    User getUser();

    void setUser(User user);
}
