package com.chat.handlers;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public abstract class ResponseHandler extends Thread {

    private static Logger logger = Logger.getLogger(ResponseHandler.class);
    protected final BufferedReader in;
    private boolean listening = true;

    public ResponseHandler(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (listening) {
                processInput(in.readLine());
            }
        } catch (IOException e) {
            logger.error("Error reading data.", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    protected abstract void processInput(String message) throws IOException;

    public void stopHandler() {
        listening = false;
    }
}
