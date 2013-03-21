package com.chat.connection;

import com.chat.model.User;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Connection {
    private final Socket messagesSocket;
    private final Socket metaInfSocket;
    private final BufferedReader messagesReader;
    private final BufferedReader metaInfReader;
    private final PrintWriter messagesWriter;
    private final PrintWriter metaInfWriter;

    public ClientConnection(String address, int messagesPort, int metaInfPort) throws IOException {
        this.messagesSocket = new Socket(address, messagesPort);
        this.metaInfSocket = new Socket(address, metaInfPort);
        this.messagesReader = new BufferedReader(new InputStreamReader(messagesSocket.getInputStream()));
        this.metaInfReader = new BufferedReader(new InputStreamReader(metaInfSocket.getInputStream()));
        this.messagesWriter = new PrintWriter(messagesSocket.getOutputStream(), true);
        this.metaInfWriter = new PrintWriter(metaInfSocket.getOutputStream(), true);
    }

    public String readMessage() throws IOException {
        return messagesReader.readLine();
    }

    public String readMetaInf() throws IOException {
        return metaInfReader.readLine();
    }

    public void writeMessage(String message) {
        messagesWriter.println(message);
    }

    public void writeMetaInf(String message) {
        metaInfWriter.println(message);
    }

    public void close() {
        IOUtils.closeQuietly(messagesReader);
        IOUtils.closeQuietly(messagesWriter);
        IOUtils.closeQuietly(messagesSocket);
        IOUtils.closeQuietly(metaInfReader);
        IOUtils.closeQuietly(metaInfWriter);
        IOUtils.closeQuietly(metaInfSocket);
    }

    @Override
    public User getUser() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
