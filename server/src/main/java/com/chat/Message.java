package com.chat;

import com.chat.connection.Connection;

import java.util.Date;

public class Message {
    public Connection from;
    public String message;
    public Date date;
    public String groupId;

    public Message(Connection from, String message) {
        this.from = from;
        this.message = message;
        this.date = new Date();
        this.groupId = DataHolder.DEFAULT_GROUP;
    }
}
