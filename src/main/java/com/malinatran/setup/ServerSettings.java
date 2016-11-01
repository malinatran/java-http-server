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
            printAndTerminate("port", tempPort);
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

    private String getDirectoryOrErrorMessage(String validDirectory, String tempDirectory) {
        if (InputValidator.isValidDirectory(tempDirectory)) {
            return tempDirectory;
        } else  {
            printAndTerminate("directory", tempDirectory);
        }

        return validDirectory;
    }

    private void printAndTerminate(String key, String value) {
        System.out.println("Looks like the " + key + " " + value + " is invalid. Try again?");
        System.exit(0);
    }
}
