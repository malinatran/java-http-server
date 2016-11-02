package com.malinatran.setup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandLineArgsParser {

    private Map<String, String> configuration;

    public CommandLineArgsParser(String[] args) {
        int size = args.length;
        this.configuration = new HashMap<String, String>();
        String currentKey = "";

        for (int i = 0; i < size; i++) {
            String currentCharacter = args[i];

            if (currentCharacter == "-p" || currentCharacter == "-d") {
                currentKey = currentCharacter;
            } else {
                if (!currentKey.isEmpty()) {
                    configuration.put(currentKey, currentCharacter);
                }
            }
        }
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public Boolean isValid() {
        return isInteger(configuration.get("-p")) && isNotAFlag(configuration.values());
    }

    private Boolean isInteger(String element) {
        return element.matches("^-?\\d+$");
    }

    private Boolean isNotAFlag(Collection<String> elements) {
        for (String element : elements) {
            if (element.startsWith("-p") || element.startsWith("-d")) {
                return false;
            }
        }

        return true;
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
