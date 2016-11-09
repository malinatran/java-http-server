package com.malinatran.setup;

import java.util.Arrays;

public class ErrorHandler {

    public static final String PORT = "port";
    public static final String DIRECTORY = "directory";
    public static final String ARGS = "args";
    public static final String BUSY = "Busy";
    public static final String INVALID = "Invalid";
    public static final String FORMATTING_RULES = "\nValid formatting includes:\n `-p PORT`\n `-d DIRECTORY`\n `-p PORT -d DIRECTORY`";

    public static void print(String[] args) {
        printMessage(ARGS, Arrays.toString(args), INVALID);
        printFormattingRules();
    }

    public static void print(String key, Integer port, String error) {
        printMessage(key, String.valueOf(port), error);
    }

    public static void print(String key, String directory, String error) {
        printMessage(key, directory, error);
    }

    private static void printFormattingRules() {
       System.out.println(FORMATTING_RULES);
    }

    private static void printMessage(String key, String value, String error) {
        System.out.println(error + " " + key + ": " + value + ". Exiting now.");
    }
}