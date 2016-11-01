package com.malinatran.setup;

import java.util.HashMap;
import java.util.Map;

public class CommandLineArgsParser {

    private Map<String, String> configuration;

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public CommandLineArgsParser(String[] args) {
        int size = args.length;

        if (size == 4 || size == 0) {
            setConfiguration(args);
        } else if (size == 2) {
            setConfigurationIfFlagsExist(args);
        } else {
            throwInvalidArgsError(args);
        }
    }

    private void setConfigurationIfFlagsExist(String[] args) {
        if (InputValidator.isFlag(args[0])) {
            setConfiguration(args);
        } else {
            throwInvalidArgsError(args);
        }
    }

    private void setConfiguration(String[] args) {
        String currentKey = "";
        this.configuration = new HashMap<String, String>();

        for (int i = 0; i < args.length; i++) {
            String currentArg = args[i];

            if (InputValidator.isFlag(currentArg)) {
                currentKey = currentArg;
            } else if (!currentKey.isEmpty()) {
                configuration.put(currentKey, InputValidator.addFileSeparators(currentKey, currentArg));
                currentKey = "";
            }
        }
    }

    private void throwInvalidArgsError(String[] args) {
        ErrorHandler.print(args);
        System.exit(0);
    }
}
