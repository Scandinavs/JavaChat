package com.chat.model.message;

import com.chat.MessageProcessor;

import java.io.Serializable;

public interface Message extends Serializable {

    void process(MessageProcessor messageProcessor);
}
