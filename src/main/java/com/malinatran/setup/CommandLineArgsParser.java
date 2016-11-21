package com.malinatran.setup;

import java.util.Hashtable;
import java.util.Map;

public class CommandLineArgsParser {

    private String[] args;
    private Map<String, String> configuration;
    private final int NO_ARGS = 0;
    private final int TWO_ARGS = 2;
    private final int FOUR_ARGS = 4;

    public CommandLineArgsParser(String[] args) {
        this.args = args;
        this.configuration = new Hashtable<String, String>();
    }

    public Map<String, String> configure() {
        int numberOfArgs = args.length;

        switch(numberOfArgs) {
            case NO_ARGS:
                setConfiguration();
                break;
            case TWO_ARGS:
                setConfigurationIfFlagExists();
                break;
            case FOUR_ARGS:
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

        for (String arg : args) {

            if (InputValidator.isFlag(arg)) {
                currentKey = arg;
            } else if (!currentKey.isEmpty()) {
                storeKeyAndValue(currentKey, arg);
                currentKey = "";
            }
        }

        return configuration;
    }

    private Map<String, String> storeKeyAndValue(String key, String arg) {
        String value = InputValidator.addFileSeparators(key, arg);
        configuration.put(key, value);

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