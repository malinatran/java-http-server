package com.malinatran.utility;

public class RangeParser {

    public static String[] getValues(String header) {
        String[] values = new String[2];

        int startDelimiter = header.indexOf("=");
        int endDelimiter = header.indexOf("-");
        String start = header.substring(startDelimiter + 1, endDelimiter);
        String end = header.substring(endDelimiter + 1, header.length());
        values[0] = start;
        values[1] = end;

        return values;
    }
}