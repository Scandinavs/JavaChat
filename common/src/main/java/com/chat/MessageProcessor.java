package com.chat;

import com.chat.model.FileMessage;
import com.chat.model.MetaInfMessage;
import com.chat.model.TextMessage;

public interface MessageProcessor {
    void processTextMessage(TextMessage message);

    void processFileMessage(FileMessage message);

    void processMetaInfMessage(MetaInfMessage message);
}
