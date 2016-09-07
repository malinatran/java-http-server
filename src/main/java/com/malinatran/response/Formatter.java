package com.malinatran.response;

import java.util.Iterator;
import java.util.Map;

public class Formatter {

    public static String formatHeaderLines(Map headers) {
        String headerLines = "";
        Iterator iterator = headers.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            headerLines += addNewLine(pair.getKey() + ": " + pair.getValue());
            iterator.remove();
        }

        return headerLines;
    }

    public static String addNewLine(String line) {
        return line + "\r\n";
    }
}
