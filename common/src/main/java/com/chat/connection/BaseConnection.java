package com.chat.connection;

import com.chat.model.Message;
import com.chat.model.User;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BaseConnection implements Connection {
    private final Logger logger = Logger.getLogger(this.getClass());

    protected Socket messagesSocket;
    protected Socket metaInfSocket;
    protected ObjectInputStream messagesReader;
    protected ObjectInputStream metaInfReader;
    protected ObjectOutputStream messagesWriter;
    protected ObjectOutputStream metaInfWriter;
    protected User user;

    public BaseConnection(String address, int messagesPort, int metaInfPort) throws IOException {
        this(new Socket(address, messagesPort), new Socket(address, metaInfPort));
    }

    public BaseConnection(Socket messagesSocket, Socket metaInfSocket) throws IOException {
        Validate.notNull(messagesSocket, "MessagesSocket shouldn't be null");
        Validate.notNull(metaInfSocket, "MetaInfSocket shouldn't be null");

        this.messagesSocket = messagesSocket;
        this.metaInfSocket = metaInfSocket;
        this.messagesWriter = new ObjectOutputStream(messagesSocket.getOutputStream());
        this.metaInfWriter = new ObjectOutputStream(metaInfSocket.getOutputStream());
        this.messagesReader = new ObjectInputStream(messagesSocket.getInputStream());
        this.metaInfReader = new ObjectInputStream(metaInfSocket.getInputStream());
    }

    @Override
    public Message readMessage() throws IOException {
        return read(messagesReader);
    }

    @Override
    public Message readMetaInf() throws IOException {
        return read(metaInfReader);
    }

    @Override
    public void writeMessage(Message message) throws IOException {
        messagesWriter.writeObject(message);
    }

    @Override
    public void writeMetaInf(Message message) throws IOException {
        metaInfWriter.writeObject(message);
    }

    @Override
    public void close() {
        IOUtils.closeQuietly(messagesSocket);
        IOUtils.closeQuietly(metaInfSocket);
        IOUtils.closeQuietly(messagesReader);
        IOUtils.closeQuietly(messagesWriter);
        IOUtils.closeQuietly(metaInfReader);
        IOUtils.closeQuietly(metaInfWriter);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    private Message read(ObjectInputStream reader) throws IOException {
        try {
            return (Message) reader.readObject();
        } catch (ClassNotFoundException e) {
            logger.error("Error reading object.", e);
            return null;
        }
    }
}
