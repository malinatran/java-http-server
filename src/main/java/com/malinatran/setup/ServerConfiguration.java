package com.malinatran.setup;

import com.malinatran.utility.FileSeparator;

import java.util.Hashtable;
import java.util.Map;

public class ServerConfiguration {

    private Map<String, String> settings;
    private PortArg portArg;
    private DirectoryArg directoryArg;
    private int port;
    private String directory;

    public ServerConfiguration(String[] args) {
        this.settings = new Hashtable<String, String>();
        this.portArg = new PortArg();
        this.directoryArg = new DirectoryArg();

        parseArgs(args);
        setArgValues();
    }

    public int getPort() {
        return port;
    }

    public String getDirectory() {
        return directory;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    private Map<String, String> parseArgs(String[] args) {
        String currentKey = "";

        for (String arg : args) {
            if (isFlag(arg)) {
                currentKey = arg;
            } else if (!currentKey.isEmpty()) {
                String formattedValue = addFileSeparators(currentKey, arg);
                settings.put(currentKey, formattedValue);
                currentKey = "";
            }
        }

        return settings;
    }

    private void setArgValues() {
        this.port = portArg.setInteger(settings);
        this.directory = directoryArg.setString(settings);
    }

    private boolean isFlag(String arg) {
        return portArg.isFlag(arg) || directoryArg.isFlag(arg);
    }

    private String addFileSeparators(String key, String arg) {
        if (isValidPath(key, arg)) {
            return FileSeparator.format(arg);
        }

        return arg;
    }

    private boolean isValidPath(String key, String arg) {
        return (!portArg.isValidInteger(arg) && directoryArg.isFlag(key));
    }
}