package com.malinatran.resource;

import java.util.Map;

public class ContentRangeHelper {

    public static final String START = "Start";
    public static final String END = "End";

    public static int[] getRange(Map<String, Integer> range, int count) {
        int[] contentRange = new int[2];

        if (hasStartAndEndRange(range)) {
            contentRange[0] = range.get(START);
            contentRange[1] = range.get(END);
        } else if (hasStartRangeOnly(range)) {
            contentRange[0] = range.get(START);
            contentRange[1] = count;
        } else if (hasEndRangeOnly(range)) {
            contentRange[0] = count - (range.get(END) - 1);
            contentRange[1] = count;
        }

        return contentRange;
    }

    private static boolean hasEndRangeOnly(Map<String, Integer> range) {
        return !has(range, START) && has(range, END);
    }

    private static boolean hasStartAndEndRange(Map<String, Integer> range) {
        return has(range, START) && has(range, END);
    }

    private static boolean hasStartRangeOnly(Map<String, Integer> range) {
        return has(range, START) && !has(range, END);
    }

    private static boolean has(Map<String, Integer> range, String key) {
        return range.containsKey(key);
    }
}