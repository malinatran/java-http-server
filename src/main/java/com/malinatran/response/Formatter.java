package com.malinatran.response;

import java.util.Map;

public class Formatter {

    public static final String LF = "\n";
    public static final String CRLF = "\r\n";

    public static String addEOFCharacter(int end, int count) {
        return (end == count ? LF : "");
    }

    public static String addLF(String line) {
        return line + LF;
    }

    public static String addCRLF(String line) {
        return line + CRLF;
    }

    public static String formatHeaderLines(Map<String, String> headers) {
        String headerLines = "";

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            headerLines += addCRLF(String.format("%s: %s", key, value));
        }

        return addCRLF(headerLines);
    }
}