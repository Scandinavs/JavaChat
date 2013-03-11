package com.chat.messagesender;

import com.chat.DataHolder;
import com.chat.Message;
import com.chat.connection.Connection;

import java.util.List;

public class DefaultMessageSender extends MessageSender {

    @Override
    protected List<Message> getMessages() {
        return DataHolder.INSTANCE.getDefaultGroupMessages();
    }

    @Override
    protected List<Connection> getConnections() {
        return DataHolder.INSTANCE.getDefaultGroupConnections();
    }
}
