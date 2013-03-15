package com.chat.connection;

import com.chat.model.User;

import java.io.IOException;

public interface Connection {
    String read() throws IOException;

    void write(String message);

    String readMetaInf() throws IOException;

    void writeMetaInf(String message);

    void close();

    User getUser();

    String getGroup();

    void setGroup(String group);
}
