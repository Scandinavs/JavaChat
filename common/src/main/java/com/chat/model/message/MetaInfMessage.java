package com.chat.model.message;

import com.chat.MessageProcessor;
import com.chat.model.user.User;

import java.util.List;
import java.util.Set;

public class MetaInfMessage implements Message {
    private User currentUser;
    private List<Action> actions;
    private String description;

    /**
     * Server side data. Shouldn't be set by the client application.
     */
    private Set<User> usersOnline;

    public MetaInfMessage(User currentUser) {
        this.currentUser = currentUser;
    }

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

    @Override
    public String toString() {
        return "MetaInfMessage{" +
                "currentUser=" + currentUser +
                '}';
    }
}
