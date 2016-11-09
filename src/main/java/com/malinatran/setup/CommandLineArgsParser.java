package com.malinatran.setup;

import java.util.Hashtable;
import java.util.Map;

public class CommandLineArgsParser {

    private String[] args;
    private Map<String, String> configuration;

    public CommandLineArgsParser(String[] args) {
        this.args = args;
        this.configuration = new Hashtable<String, String>();
    }

    public Map<String, String> configure() {
        int size = args.length;

        switch(size) {
            case 0:
                setConfiguration();
                break;
            case 2:
                setConfigurationIfFlagExists();
                break;
            case 4:
                setConfiguration();
                break;
            default:
                throwInvalidArgsError();
        }

        return configuration;
    }

    private boolean isFirstArgAFlag(String arg) {
        return (InputValidator.isFlag(arg));
    }

    private Map<String, String> setConfiguration() {
        String currentKey = "";

        for (int i = 0; i < args.length; i++) {
            String currentArg = args[i];

            if (InputValidator.isFlag(currentArg)) {
                currentKey = currentArg;
            } else if (!currentKey.isEmpty()) {
                configuration.put(currentKey, InputValidator.addFileSeparators(currentKey, currentArg));
                currentKey = "";
            }
        }

        return configuration;
    }

    private Map<String, String> setConfigurationIfFlagExists() {
        if (isFirstArgAFlag(args[0])) {
            setConfiguration();
        } else {
            throwInvalidArgsError();
        }

        return configuration;
    }

    private void throwInvalidArgsError() {
        ErrorHandler.print(args);
        System.exit(0);
    }
}
