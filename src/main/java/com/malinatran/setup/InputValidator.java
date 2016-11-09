package com.malinatran.setup;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputValidator {

    public static String addFileSeparators(String flag, String arg) {
        if (!isInteger(arg) && isDirectoryFlag(flag)) {
            if (doesNotHaveFileSeparators(arg)) {
                return "/" + arg + "/";
            } else if (hasStartFileSeparator(arg)) {
                return arg + "/";
            } else if (hasEndFileSeparator(arg)) {
                return "/" + arg;
            } else {
                return arg;
            }
        }

        return arg;
    }

    public static boolean isFlag(String arg) {
        return isPortFlag(arg) || isDirectoryFlag(arg);
    }

    public static boolean isValidDirectory(String directory) {
        Path path = Paths.get(directory);

        return Files.exists(path);
    }

    private static boolean doesNotHaveFileSeparators(String arg) {
        return (!arg.startsWith("/") && !arg.endsWith("/"));
    }

    private static boolean hasEndFileSeparator(String arg) {
        return (arg.endsWith("/") && !arg.startsWith("/"));
    }

    private static boolean hasStartFileSeparator(String arg) {
        return (arg.startsWith("/") && !arg.endsWith("/"));
    }

    private static boolean isInteger(String arg) {
        return arg.matches("^-?\\d+$");
    }

    private static boolean isPortFlag(String arg) {
        return (arg.equals(ServerSettings.PORT_FLAG));
    }

    private static boolean isDirectoryFlag(String arg) {
        return arg.equals(ServerSettings.DIRECTORY_FLAG);
    }
}