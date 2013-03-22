package com.chat;

import com.chat.model.message.FileMessage;
import com.chat.model.message.MetaInfMessage;
import com.chat.model.message.TextMessage;

public interface MessageProcessor {
    void processTextMessage(TextMessage message);

    void processFileMessage(FileMessage message);

    void processMetaInfMessage(MetaInfMessage message);
}
