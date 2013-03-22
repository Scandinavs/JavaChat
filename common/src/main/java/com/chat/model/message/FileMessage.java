package com.chat.model.message;

import com.chat.MessageProcessor;

public class FileMessage implements Message {
    @Override
    public void process(MessageProcessor messageProcessor) {
        messageProcessor.processFileMessage(this);
    }
}
