package com.chat.model;

import com.chat.MessageProcessor;

import java.util.List;
import java.util.Set;

public class MetaInfMessage implements Message {
    private String status;
    private List<Action> actions;
    private String description;
    private User currentUser;
    private Set<User> usersOnline;

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

    public Set<User> getUsersOnline() {
        return usersOnline;
    }

    public void setUsersOnline(Set<User> usersOnline) {
        this.usersOnline = usersOnline;
    }
}
