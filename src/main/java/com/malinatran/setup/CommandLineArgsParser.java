package com.malinatran.setup;

import java.util.HashMap;
import java.util.Map;

public class CommandLineArgsParser {

    private Map<String, String> configuration;

    public CommandLineArgsParser(String[] args) {
        int size = args.length;
        this.configuration = new HashMap<String, String>();
        String currentKey = "";

        for (int i = 0; i < size; i++) {
            String currentArg = args[i];

            if (currentArg.equals("-p") || currentArg.equals("-d")) {
                currentKey = currentArg;
            } else if (!currentKey.isEmpty()) {
                configuration.put(currentKey, addFileSeparators(currentArg));
                currentKey = "";
            }
        }
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    private String addFileSeparators(String element) {
        if (!isInteger(element)) {
            if (doesNotHaveFileSeparators(element)) {
                return "/" + element + "/";
            } else if (!startsWithFileSeparator(element)) {
                return "/" + element;
            } else if (!endsWithFileSeparator(element)) {
                return element + "/";
            } else {
                return element;
            }
        }

        return element;
    }


    private Boolean isInteger(String element) {
        return element.matches("^-?\\d+$");
    }

    private Boolean doesNotHaveFileSeparators(String element) {
        return (!startsWithFileSeparator(element) && !endsWithFileSeparator(element));
    }

    private Boolean startsWithFileSeparator(String element) {
        return (element.startsWith("/"));
    }

    private Boolean endsWithFileSeparator(String element) {
        return (element.endsWith("/"));
    }
}
