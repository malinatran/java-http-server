package com.malinatran.setup;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputValidator {

    public static Boolean isFlag(String arg) {
        return (arg.equals("-p") || arg.equals("-d"));
    }

    public static String addFileSeparators(String key, String arg) {
        if (!isInteger(arg) && key.equals("-d")) {
            if (doesNotHaveFileSeparators(arg)) {
                return "/" + arg + "/";
            } else if (startsWithFileSeparatorOnly(arg)) {
                return arg + "/";
            } else if (endsWithFileSeparatorOnly(arg)) {
                return "/" + arg;
            } else {
                return arg;
            }
        }

        return arg;
    }

    public static Boolean isValidDirectory(String directory) {
        Path path = Paths.get(directory);

        return Files.exists(path);
    }

    public static Boolean isInteger(String arg) {
        return arg.matches("^-?\\d+$");
    }

    private static Boolean doesNotHaveFileSeparators(String arg) {
        return (!arg.startsWith("/") && !arg.endsWith("/"));
    }

    private static Boolean startsWithFileSeparatorOnly(String arg) {
        return (arg.startsWith("/") && !arg.endsWith("/"));
    }

    private static Boolean endsWithFileSeparatorOnly(String arg) {
        return (arg.endsWith("/") && !arg.startsWith("/"));
    }
}
