package com.chat.messagesender;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MessageBroker {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final MessageSender sender;
    private ScheduledFuture messageSenderTask;

    public MessageBroker(MessageSender sender) {
        this.sender = sender;
    }

    public void start() {
        messageSenderTask = scheduler.scheduleAtFixedRate(sender, 0, 100, MILLISECONDS);
    }

    public void stop() {
        if (messageSenderTask != null) {
            messageSenderTask.cancel(false);
        }
    }
}
