package com.malinatran.setup;

import java.util.Map;

public class PortArg extends Arg {

    public static final int DEFAULT_PORT = 5000;
    public static final String PORT = "Port";
    public static final String FLAG = "-p";
    private static final String DIGITS_REGEX = "^-?\\d+$";

    public boolean isFlag(String arg) {
        return arg.equals(FLAG);
    }

    public boolean isInteger(String arg) {
        return arg.matches(DIGITS_REGEX);
    }

    public int setInteger(Map<String, String> configuration) {
        int valid = DEFAULT_PORT;
        String port = configuration.get(FLAG);

        if (port != null) {
            valid = getPortOrErrorMessage(valid, port);
        }

        return valid;
    }

    private int getPortOrErrorMessage(int valid, String temp) {
        try {
            return Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            printError(temp);
        }

        return valid;
    }

    void printError(String port) {
        CommandLinePrinter.print(PORT, port, INVALID);
        System.exit(-1);
    }
}