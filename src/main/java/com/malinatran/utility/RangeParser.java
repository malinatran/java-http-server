package com.malinatran.utility;

public class RangeParser {

    public static String[] getValues(String header) {
        int startDelimiter = header.indexOf("=");
        int endDelimiter = header.indexOf("-");
        String rangeStart = header.substring(startDelimiter + 1, endDelimiter);
        String rangeEnd = header.substring(endDelimiter + 1, header.length());
        String[] rangeValues = new String[2];
        rangeValues[0] = rangeStart;
        rangeValues[1] = rangeEnd;

        return rangeValues;
    }
}
