package com.malinatran.setup;

import java.util.Map;

public class ServerSettings {

    public static final String PORT_FLAG = "-p";
    public static final String DIRECTORY_FLAG = "-d";
    public static final int DEFAULT_PORT = 5000;
    public static final String DEFAULT_DIRECTORY = "/public/";
    public static final String HOME_DIRECTORY = System.getProperty("user.dir");
    private Map<String, String> configuration;
    private int port;
    private String directory;

    public ServerSettings(Map<String, String> configuration) {
        this.configuration = configuration;
        this.port = getPort();
        this.directory = getDirectory();
    }

    public int getPort() {
        int validPort = DEFAULT_PORT;
        String tempPort = configuration.get(PORT_FLAG);

        if (hasFlag(PORT_FLAG)) {
            validPort = getPortOrErrorMessage(validPort, tempPort);
        }

        return validPort;
    }

    public String getDirectory() {
        String validDirectory = HOME_DIRECTORY + DEFAULT_DIRECTORY;
        String tempDirectory = HOME_DIRECTORY + configuration.get(DIRECTORY_FLAG);

        if (hasFlag(DIRECTORY_FLAG)) {
            validDirectory = getDirectoryOrErrorMessage(validDirectory, tempDirectory);
        }

        return validDirectory;
    }

    private String getDirectoryOrErrorMessage(String validDirectory, String tempDirectory) {
        if (InputValidator.isValidDirectory(tempDirectory)) {
            return tempDirectory;
        } else  {
            printAndTerminate(ErrorHandler.DIRECTORY, tempDirectory);
        }

        return validDirectory;
    }

    private int getPortOrErrorMessage(int validPort, String tempPort) {
        try {
            return Integer.parseInt(tempPort);
        } catch (Exception e) {
            printAndTerminate(ErrorHandler.PORT, tempPort);
        }

        return validPort;
    }

    private boolean hasFlag(String flagType) {
        return configuration.containsKey(flagType);
    }

    protected void printAndTerminate(String key, String value) {
        ErrorHandler.print(key, value, ErrorHandler.INVALID);
        System.exit(0);
    }
}