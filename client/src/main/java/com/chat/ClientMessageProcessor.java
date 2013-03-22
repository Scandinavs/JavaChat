package com.chat;

import com.chat.model.message.FileMessage;
import com.chat.model.message.MetaInfMessage;
import com.chat.model.message.TextMessage;
import com.chat.ui.OutputInterface;

public class ClientMessageProcessor implements MessageProcessor {

    private OutputInterface out;

    public ClientMessageProcessor(OutputInterface out) {
        this.out = out;
    }

    @Override
    public void processTextMessage(TextMessage message) {
        out.write(message);
    }

    @Override
    public void processFileMessage(FileMessage message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void processMetaInfMessage(MetaInfMessage message) {
        DataHolder.getUsersOnline().clear();
        DataHolder.getUsersOnline().addAll(message.getUsersOnline());
        if (message.toString().equals("Connections count: 3")) {
            System.out.println(message);
        }
    }
}
