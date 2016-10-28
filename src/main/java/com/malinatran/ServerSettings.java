package com.malinatran;

import java.util.Map;

public class ServerSettings {

    private int port;
    private String path;
    private int DEFAULT_PORT = 5000;
    private String DEFAULT_PATH = "/Development/cob_spec/public/";
    private String directoryPath = System.getProperty("user.home");
    private Map<String, String> portAndDirectoryPath;

    public ServerSettings(Map<String, String> configuration) {
        this.portAndDirectoryPath = configuration;
        this.port = getPort();
        this.path = getDirectory();
    }

    public int getPort() {
       return (portAndDirectoryPath.containsKey("Port") ? Integer.parseInt(portAndDirectoryPath.get("Port")) : DEFAULT_PORT);
    }

    private String getDirectory() {
       return (portAndDirectoryPath.containsKey("Directory") ? portAndDirectoryPath.get("Directory") : DEFAULT_PATH);
    }

    public String getPath() {
        return directoryPath + path;
    }
}
