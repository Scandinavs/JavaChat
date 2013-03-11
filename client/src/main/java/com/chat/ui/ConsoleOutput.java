package com.chat.ui;

public class ConsoleOutput implements OutputInterface {
    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
