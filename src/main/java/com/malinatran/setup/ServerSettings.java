package com.malinatran.setup;

import java.util.Map;

public class ServerSettings {

    private int port;
    private String path;
    private String PORT_FLAG = "-p";
    private String DIRECTORY_FLAG = "-d";
    private int DEFAULT_PORT = 5000;
    private String DEFAULT_PATH = "/Development/cob_spec/public/";
    private String home = System.getProperty("user.home");
    private Map<String, String> configuration;

    public ServerSettings(Map<String, String> configuration) {
        this.configuration = configuration;
        this.port = getPort();
        this.path = getDirectory();
    }

    public int getPort() {
       return (configuration.containsKey(PORT_FLAG) ? Integer.parseInt(configuration.get(PORT_FLAG)) : DEFAULT_PORT);
    }

    public String getDirectory() {
       return home + ((configuration.containsKey(DIRECTORY_FLAG) ? configuration.get(DIRECTORY_FLAG) : DEFAULT_PATH));
    }
}
