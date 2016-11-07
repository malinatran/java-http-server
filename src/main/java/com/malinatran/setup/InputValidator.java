package com.malinatran.setup;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputValidator {

    public static boolean isFlag(String arg) {
        return (arg.equals(ServerSettings.PORT_FLAG) || arg.equals(ServerSettings.DIRECTORY_FLAG));
    }

    public static String addFileSeparators(String key, String arg) {
        if (!isInteger(arg) && key.equals(ServerSettings.DIRECTORY_FLAG)) {
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

    public static boolean isValidDirectory(String directory) {
        Path path = Paths.get(directory);

        return Files.exists(path);
    }

    public static boolean isInteger(String arg) {
        return arg.matches("^-?\\d+$");
    }

    private static boolean doesNotHaveFileSeparators(String arg) {
        return (!arg.startsWith("/") && !arg.endsWith("/"));
    }

    private static boolean startsWithFileSeparatorOnly(String arg) {
        return (arg.startsWith("/") && !arg.endsWith("/"));
    }

    private static boolean endsWithFileSeparatorOnly(String arg) {
        return (arg.endsWith("/") && !arg.startsWith("/"));
    }
}
