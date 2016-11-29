package com.malinatran.setup;

import static com.malinatran.setup.DirectoryArg.DIRECTORY;
import static com.malinatran.setup.PortArg.PORT;

public class CommandLinePrinter {

    public static void print(int port, String directory) {
        print(PORT, String.valueOf(port));
        print(DIRECTORY, directory);
    }

    public static void print(String key, String value, String error) {
        print(error + " " + key.toLowerCase(), value + ". Exiting now.");
    }

    private static void print(String first, String second) {
        print(String.format("%s: %s", first, second));
    }

    private static void print(String message) {
        System.out.println(message);
    }
}