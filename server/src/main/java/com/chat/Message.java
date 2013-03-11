package com.chat;

import com.chat.connection.Connection;

import java.util.Date;

public class Message {
    private Connection from;
    private String message;
    private Date date;
    private String groupId;

    public Message(Connection from, String message) {
        this.from = from;
        this.message = message;
        this.date = new Date();
        this.groupId = DataHolder.DEFAULT_GROUP;
    }

    public Connection getFrom() {
        return from;
    }

    public void setFrom(Connection from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getDate() {
        return date;
    }
}
