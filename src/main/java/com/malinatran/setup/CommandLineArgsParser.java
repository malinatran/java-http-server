package com.malinatran.setup;

import java.util.HashMap;
import java.util.Map;

public class CommandLineArgsParser {

    private Map<String, String> configuration;

    public CommandLineArgsParser(String[] args) {
        int size = args.length;
        this.configuration = new HashMap<String, String>();

        for (int i = 0; i < size; i++) {
            String current = args[i];

            if (isInteger(current)) {
                configuration.put("Port", current);
            } else if (isNotAFlag(current)) {
                configuration.put("Directory", addFileSeparators(current));
            }
        }
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    private Boolean isInteger(String element) {
        return element.matches("^-?\\d+$");
    }

    private Boolean isNotAFlag(String element) {
        return (!element.contains("-d") && !element.contains("-p"));
    }

    private String addFileSeparators(String element) {
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
