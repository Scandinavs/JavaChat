package com.chat;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerMessageHandler extends Thread {

    private BufferedReader in;
    private boolean listening = true;

    public ServerMessageHandler(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (listening) {
                if (in.ready()) {
                    String fromServer = in.readLine();
                    System.out.println(fromServer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public void stopHandler() {
        listening = false;
    }
}