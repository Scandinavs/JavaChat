package com.chat.messagesender;

import com.chat.Message;
import com.chat.connection.Connection;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Iterator;
import java.util.List;

public abstract class MessageSender extends Thread {

    {
        setDaemon(true);
    }

    @Override
    public void run() {
        synchronized (getMessages()) {
            Iterator<Message> iterator = getMessages().iterator();
            while (iterator.hasNext()) {
                sendMessage(iterator.next());
                iterator.remove();
            }
        }
    }

    protected abstract List<Message> getMessages();

    protected abstract List<Connection> getConnections();

    private void sendMessage(Message message) {
        for (Connection connection : getConnections()) {
            String date = DateFormatUtils.format(message.date, "hh:mm:ss");
            connection.write(String.format("|%s| %s : %s", date, message.from.getUser().getName(), message.message));
        }
    }
}
