package com.chat.messagesender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MessageBroker {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final List<Runnable> senderList;
    private List<ScheduledFuture> messageSenderTaskList = new ArrayList<>();

    public MessageBroker(List<Runnable> senderList) {
        this.senderList = senderList;
    }

    public void start() {
        for (Runnable sender : senderList) {
            messageSenderTaskList.add(scheduler.scheduleAtFixedRate(sender, 0, 100, MILLISECONDS));
        }
    }

    public void stop() {
        if (messageSenderTaskList != null) {
            for (ScheduledFuture messageSenderTask : messageSenderTaskList) {
                messageSenderTask.cancel(false);
            }
        }
    }
}
