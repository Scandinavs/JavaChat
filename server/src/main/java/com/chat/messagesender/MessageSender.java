package com.chat.messagesender;

import com.chat.DataHolder;
import com.chat.Message;
import com.chat.connection.Connection;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Iterator;
import java.util.List;

public class MessageSender extends Thread {

    private final String group;

    public MessageSender() {
        this(DataHolder.DEFAULT_GROUP);
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
        return DataHolder.INSTANCE.getGroupMessages(group);
    }

    protected List<Connection> getConnections() {
        return DataHolder.INSTANCE.getGroupConnections(group);
    }

    private void sendMessage(Message message) {
        final List<Connection> connections = getConnections();
        if (connections == null) {
            return;
        }
        synchronized (getConnections()) {
            for (Connection connection : connections) {
                String date = DateFormatUtils.format(message.getDate(), "hh:mm:ss");
                connection.write(String.format("|%s| %s : %s", date, message.getFrom().getUser().getName(), message.getMessage()));
            }
        }
    }
}
