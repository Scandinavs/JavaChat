package com.chat.model;

import com.chat.connection.Connection;

import java.util.Date;

public class TextMessage implements Message {
    private Connection from;
    private String message;
    private Date date;

    public TextMessage(Connection from, String message) {
        this.from = from;
        this.message = message;
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }
}
