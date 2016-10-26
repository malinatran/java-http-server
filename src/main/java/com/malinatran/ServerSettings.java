package com.malinatran;

import java.util.Map;

public class ServerSettings {

    private int port;
    private String path;
    private int DEFAULT_PORT = 5000;
    private String DEFAULT_PATH = "/Development/cob_spec/public/";
    private String directoryPath = System.getProperty("user.home") + path;

    public ServerSettings(Map<String, String> configuration) {
        this.port = getPort(configuration);
        this.path = getDirectory(configuration);
    }

    private int getPort(Map<String, String> configuration) {
       return (configuration.containsKey("Port") ? Integer.parseInt(configuration.get("Port")) : DEFAULT_PORT);
    }

    private String getDirectory(Map<String, String> configuration) {
       return (configuration.containsKey("Directory") ? configuration.get("Directory") : DEFAULT_PATH);
    }

    public int getPort() {
        return port;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
