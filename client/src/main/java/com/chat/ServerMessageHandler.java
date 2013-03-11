package com.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerMessageHandler extends Thread {

    private Socket socket;

    public ServerMessageHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean listening = true;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (listening) {
                String fromServer = in.readLine();
                System.out.println(fromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}