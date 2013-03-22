package com.chat.ui;

import com.chat.model.message.TextMessage;

public interface OutputInterface {

    void write(TextMessage message);
}
