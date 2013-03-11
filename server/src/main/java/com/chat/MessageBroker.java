package com.chat;

import com.chat.connection.Connection;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MessageBroker {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture messageSender;

    public static void start() {
        messageSender = scheduler.scheduleAtFixedRate(new MessageSender(), 0, 100, MILLISECONDS);
    }

    public static void stop() {
        if (messageSender != null) {
            messageSender.cancel(false);
        }
    }

    private static class MessageSender extends Thread {

        private MessageSender() {
            setDaemon(true);
        }

        @Override
        public void run() {
            synchronized (DataHolder.INSTANCE.getMessages()) {
                Iterator<Message> iterator = DataHolder.INSTANCE.getMessages().iterator();
                while (iterator.hasNext()) {
                    sendMessage(iterator.next());
                    iterator.remove();
                }
            }
        }

        private void sendMessage(Message message) {
            for (Connection connection : DataHolder.INSTANCE.getConnections()) {
                String date = DateFormatUtils.format(message.date, "hh:mm:ss");
                connection.write(String.format("|%s| %s : %s", date, message.from.getUser().getName(), message.message));
            }
        }
    }
}
