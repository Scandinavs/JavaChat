package com.chat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class SystemProperties {

    private static final String MESSAGES_PORT = "messages.port";
    private static final String SERVICE_PORT = "service.port";
    private static final String MAX_CONNECTIONS = "max.connections";
    private static final String KEEP_MESSAGES_COUNT = "keep.messages.count";
    private static final String LOGFILE_PATH = "logfile.path";

    private static final Properties properties = new Properties(createDefaultProperties());

    private static Properties createDefaultProperties() {
        final Properties defaultProperties = new Properties();
        defaultProperties.put(MESSAGES_PORT, 4444);
        defaultProperties.put(MAX_CONNECTIONS, 4444);
        return defaultProperties;
    }

    public static void loadProperties() throws IOException {
        final InputStream inputStream = ChatServer.class.getClassLoader().getResourceAsStream("server.properties");
        properties.load(inputStream);
    }

    public static int getServicePort() {
        return Integer.parseInt(properties.getProperty(SERVICE_PORT));
    }

    public static int getMaxConnections() {
        return Integer.parseInt(properties.getProperty(MAX_CONNECTIONS));
    }

    public static int getKeepMessagesCount() {
        return Integer.parseInt(properties.getProperty(KEEP_MESSAGES_COUNT));
    }

    public static String getLogfilePath() {
        return properties.getProperty(LOGFILE_PATH);
    }

    public static int getMessagesPort() {
        return Integer.parseInt(properties.getProperty(MESSAGES_PORT));
    }
}
