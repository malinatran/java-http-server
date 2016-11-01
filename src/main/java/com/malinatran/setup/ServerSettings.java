package com.malinatran.setup;

import java.util.Map;

public class ServerSettings {

    private int port;
    private String path;
    public static final String PORT = "port";
    public static final String DIRECTORY = "directory";
    public static final String PORT_FLAG = "-p";
    public static final String DIRECTORY_FLAG = "-d";
    private static int DEFAULT_PORT = 5000;
    public static final String DEFAULT_PATH = "/Development/cob_spec/public/";
    private String home = System.getProperty("user.home");
    private Map<String, String> configuration;

    public ServerSettings(Map<String, String> configuration) {
        this.configuration = configuration;
        this.port = getPort();
        this.path = getDirectory();
    }

    public int getPort() {
        int validPort = DEFAULT_PORT;
        String tempPort = configuration.get(PORT_FLAG);

        if (configuration.containsKey(PORT_FLAG)) {
            validPort = getPortOrErrorMessage(validPort, tempPort);
        }

        return validPort;
    }

    private int getPortOrErrorMessage(int validPort, String tempPort) {
        try {
            return Integer.parseInt(tempPort);
        } catch (Exception e) {
            printAndTerminate(PORT, tempPort);
        }

        return validPort;
    }

    public String getDirectory() {
        String validDirectory = home + DEFAULT_PATH;
        String tempDirectory = home + configuration.get(DIRECTORY_FLAG);

        if (configuration.containsKey(DIRECTORY_FLAG)) {
            validDirectory = getDirectoryOrErrorMessage(validDirectory, tempDirectory);
        }

        return validDirectory;
    }

    public String getDirectoryOrErrorMessage(String validDirectory, String tempDirectory) {
        if (InputValidator.isValidDirectory(tempDirectory)) {
            return tempDirectory;
        } else  {
            printAndTerminate(DIRECTORY, tempDirectory);
        }

        return validDirectory;
    }

    protected void printAndTerminate(String key, String value) {
        System.out.println("Looks like the " + key + " " + value + " is invalid.");
        System.exit(0);
    }
}
