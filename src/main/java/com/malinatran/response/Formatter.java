package com.malinatran.response;

import java.util.Map;

public class Formatter {

    public static String addEOFCharacter(int end, int count) {
        return (end == count ?  "\n" : "");
    }

    public static String addNewLine(String line) {
        return line + "\r\n";
    }

    public static String formatHeaderLines(Map<String, String> headers) {
        String headerLines = "";

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            headerLines += addNewLine(key + ": " + value);
        }

        return addNewLine(headerLines);
    }
}