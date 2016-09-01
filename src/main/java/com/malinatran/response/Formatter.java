package com.malinatran.response;

import java.util.Iterator;
import java.util.Map;

public class Formatter {

    public static String formatHeaderLines(Map headers) {
        String headerLines = "";
        Iterator it = headers.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            headerLines += addNewLine(pair.getKey() + ": " + pair.getValue());
            it.remove();
        }

        return headerLines;
    }

    public static String addNewLine(String line) {
        return line + "\r\n";
    }
}
