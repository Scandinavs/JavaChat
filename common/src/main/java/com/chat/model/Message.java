package com.chat.model;

import com.chat.MessageProcessor;

import java.io.Serializable;

public interface Message extends Serializable {

    void process(MessageProcessor messageProcessor);
}
