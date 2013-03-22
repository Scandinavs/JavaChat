package com.chat.model.message;

import com.chat.MessageProcessor;
import com.chat.model.user.User;

import java.util.Date;

public class TextMessage implements Message {
    private User from;
    private String message;
    private Date date;

    public TextMessage(User from, String message) {
        this.from = from;
        this.message = message;
        this.date = new Date();
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
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

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processTextMessage(this);
    }
}
