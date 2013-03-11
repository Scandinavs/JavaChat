package com.chat.connection;

import com.chat.Message;
import com.chat.User;

import java.io.IOException;

public interface Connection {
    String read() throws IOException;

    void write(String message);

    void write(Message message);

    void close();

    User getUser();
}
