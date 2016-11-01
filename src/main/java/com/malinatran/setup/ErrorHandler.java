package com.malinatran.setup;

import java.util.Arrays;

public class ErrorHandler {

    public static String PORT = "port";
    public static String DIRECTORY = "directory";
    public static String ARGS = "args";
    public static String BUSY = "Busy";
    public static String INVALID = "Invalid";
    public static String FORMATTING_RULES = "\nValid formatting includes:\n `-p PORT`\n `-d DIRECTORY`\n `-p PORT -d DIRECTORY`";

    public static void print(String key, Integer port, String error) {
        printMessage(key, String.valueOf(port), error);
    }

    public static void print(String key, String directory, String error) {
        printMessage(key, directory, error);
    }

    public static void print(String[] args) {
        printMessage(ARGS, Arrays.toString(args), INVALID);
        printFormattingRules();
    }

    private static void printMessage(String key, String value, String error) {
        System.out.println(error + " " + key + ": " + value + ". Exiting now.");
    }

    private static void printFormattingRules() {
       System.out.println(FORMATTING_RULES);
    }
}
