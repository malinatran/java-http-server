package com.malinatran.response;

import java.util.Map;

public class Formatter {

    public static String formatHeaderLines(Map<String, String> headers) {
        String headerLines = "";

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            headerLines += addNewLine(key + ": " + value);
        }

        return addNewLine(headerLines);
    }

    public static String addNewLine(String line) {
        return line + "\r\n";
    }
}