package com.malinatran;

public class Formatter {

    public static String buildResponse(String[] lines) {
        StringBuilder response = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            response.append(addNewLine(lines[i]));
        }
        return response.toString();
    }

    public static String buildInitialLine(String protocol, String status) {
        return addNewLine(protocol + " " + status);
    }

    public static String addNewLine(String line) {
        return line + "\r\n";
    }
}
