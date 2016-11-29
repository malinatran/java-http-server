package com.malinatran.setup;

import java.util.Map;

public abstract class Arg {

    public static final String INVALID = "Invalid";
    public static final String BUSY = "Busy";

    String setString(Map<String, String> configuration) {
        return "";
    }

    int setInteger(Map<String, String> configuration) {
        return 0;
    }

    boolean isValidInteger(String value) {
        return false;
    }

    boolean isFlag(String arg) {
        return false;
    }

    void printError(String value) {}
}