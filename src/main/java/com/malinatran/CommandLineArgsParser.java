package com.malinatran;

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
                configuration.put("Directory", addLineSeparators(current));
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

    private String addLineSeparators(String element) {
        if (!element.startsWith("/") && !element.endsWith("/")) {
            return "/" + element + "/";
        } else if (!element.startsWith("/")) {
            return "/" + element;
        } else if (!element.endsWith("/")) {
            return element + "/";
        } else {
            return element;
        }
    }
}
