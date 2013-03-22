package com.chat.model;

import com.chat.MessageProcessor;

import java.util.List;

public class MetaInfMessage implements Message {
    private String status;
    private List<Action> actions;
    private String description;
    private User currentUser;

    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processMetaInfMessage(this);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
