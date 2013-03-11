package com.chat.messagesender;


import com.chat.DataHolder;
import com.chat.Message;
import com.chat.connection.Connection;

import java.util.List;

public class GroupMessageSender extends MessageSender {

    private final String group;

    public GroupMessageSender(String group) {
        this.group = group;
    }

    @Override
    protected List<Message> getMessages() {
        return DataHolder.INSTANCE.getGroupMessages(group);
    }

    @Override
    protected List<Connection> getConnections() {
        return DataHolder.INSTANCE.getGroupConnections(group);
    }
}
