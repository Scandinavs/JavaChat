package com.chat.messagesender;

import com.chat.model.*;
import com.chat.model.Message;
import com.chat.connection.Connection;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Iterator;
import java.util.List;

public class MessageSender extends Thread {

    private final String group;

    public MessageSender() {
        this(Constants.DEFAULT_GROUP);
    }

    public MessageSender(String group) {
        setDaemon(true);
        this.group = group;
    }

    @Override
    public void run() {
        final List<Message> messages = getMessages();
        if (messages == null) {
            return;
        }
        synchronized (getMessages()) {
            Iterator<Message> iterator = messages.iterator();
            while (iterator.hasNext()) {
                sendMessage(iterator.next());
                iterator.remove();
            }
        }
    }

    protected List<Message> getMessages() {
        return DataHolder.getGroupMessages(group);
    }

    protected List<Connection> getConnections() {
        return DataHolder.getGroupConnections(group);
    }

    private void sendMessage(Message message) {
        final List<Connection> connections = getConnections();
        if (connections == null) {
            return;
        }
        synchronized (getConnections()) {
            for (Connection connection : connections) {
                String date = DateFormatUtils.format(((TextMessage)message).getDate(), "hh:mm:ss");
                connection.writeMessage(String.format("|%s| %s : %s", date, ((TextMessage)message).getFrom().getUser().getName(), ((TextMessage)message).getMessage()));
            }
        }
    }
}
