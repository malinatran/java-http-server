package com.malinatran.setup;

import java.util.HashMap;
import java.util.Map;

public class CommandLineArgsParser {

    private Map<String, String> configuration;
    private final String invalidEntryErrorMessage = "Hey there, that's invalid! Valid args should be formatted like so:\n `-p PORT`\n `-d DIRECTORY` \n `-p PORT -d DIRECTORY`";

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public CommandLineArgsParser(String[] args) {
        int size = args.length;

        if (size == 4) {
            setConfiguration(args);
        } else if (size == 2) {
            setConfigurationIfFlagsExist(args);
        } else {
            throwInvalidEntryError();
        }
    }

    private void setConfigurationIfFlagsExist(String[] args) {
        if (InputValidator.isFlag(args[0])) {
            setConfiguration(args);
        } else {
            throwInvalidEntryError();
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
                configuration.put(currentKey, InputValidator.addFileSeparators(currentArg));
                currentKey = "";
            }
        }
    }

    private void throwInvalidEntryError() {
        System.out.println(invalidEntryErrorMessage);
        System.exit(0);
    }

}
