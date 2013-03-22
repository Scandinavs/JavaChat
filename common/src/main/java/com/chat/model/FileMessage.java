package com.chat.model;

import com.chat.MessageProcessor;
import com.chat.connection.Connection;

import java.util.Date;

public class FileMessage implements Message {
    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processFileMessage(this);
    }
}
