package com.chat.ui;

import com.chat.model.TextMessage;
import org.apache.commons.lang.time.DateFormatUtils;

public class ConsoleOutput implements OutputInterface {
    @Override
    public void write(TextMessage message) {
        String date = DateFormatUtils.format(message.getDate(), "hh:mm:ss");
        final String formattedMessage = String.format("|%s| %s : %s", date, message.getFrom().getName(), message.getMessage());
        System.out.println(formattedMessage);
    }
}
