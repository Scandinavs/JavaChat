package com.chat;

import com.chat.ui.OutputInterface;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerMessageHandler extends Thread {

    private final OutputInterface out;
    private final BufferedReader in;
    private boolean listening = true;

    public ServerMessageHandler(Socket socket, OutputInterface out) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = out;
    }

    @Override
    public void run() {
        try {
            while (listening) {
                if (in.ready()) {
                    String fromServer = in.readLine();
                    out.write(fromServer);
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